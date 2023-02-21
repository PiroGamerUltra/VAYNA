package dev.piste.vayna.commands.slash;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.riot.InvalidRegionException;
import dev.piste.vayna.apis.riot.InvalidRiotIdException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.commands.manager.SlashCommand;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.ErrorMessages;
import dev.piste.vayna.util.templates.ReplyMessages;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

/**
 * @author Piste | https://github.com/zPiste
 */
public class StatsSlashCommand implements SlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event) throws StatusCodeException {
        event.deferReply().queue();

        Language language = LanguageManager.getLanguage(event.getGuild());

        LinkedAccount linkedAccount = null;
        RiotAccount riotAccount = null;
        if(event.getSubcommandName() == null) return;
        switch (event.getSubcommandName()) {
            // /stats me
            case "me" -> {
                linkedAccount = new LinkedAccount(event.getUser().getIdLong());
                if(linkedAccount.isExisting()) {
                    riotAccount = RiotAPI.getAccountByPuuid(linkedAccount.getRiotPuuid());
                }
            }
            // /stats user <@user>
            case "user" -> {
                linkedAccount = new LinkedAccount(event.getOption("user").getAsUser().getIdLong());
                if(linkedAccount.isExisting()) {
                    riotAccount = RiotAPI.getAccountByPuuid(linkedAccount.getRiotPuuid());
                }
            }
            // /stats riot-id <name> <tag>
            case "riot-id" -> {
                String gameName = event.getOption("name").getAsString();
                String tagLine = event.getOption("tag").getAsString();
                try {
                    riotAccount = RiotAPI.getAccountByRiotId(gameName, tagLine);

                    linkedAccount = new LinkedAccount(riotAccount.getPuuid());
                } catch (InvalidRiotIdException e) {
                    Embed embed = new Embed()
                            .setAuthor(event.getUser().getName(), event.getUser().getAvatarUrl())
                            .setColor(255, 0, 0)
                            .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-stats-error-riotid-embed-title"))
                            .setDescription(language.getTranslation("command-stats-error-riotid-embed-description")
                                    .replaceAll("%emoji:riotgames%", Emoji.getRiotGames().getFormatted())
                                    .replaceAll("%riotid%", gameName + "#" + tagLine));
                    event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                            Buttons.getSupportButton(event.getGuild())
                    ).queue();
                    return;
                }
            }
        }

        if(!linkedAccount.isExisting()) {
            if(linkedAccount.getDiscordUserId() != 0) {
                if (linkedAccount.getDiscordUserId() == event.getUser().getIdLong()) {
                    event.getHook().editOriginalEmbeds(ErrorMessages.getNoConnectionSelf(event.getGuild(), event.getUser())).setActionRow(
                            Buttons.getSupportButton(event.getGuild())
                    ).queue();
                } else {
                    event.getHook().editOriginalEmbeds(ErrorMessages.getNoConnection(event.getGuild(), event.getUser(), event.getJDA().getUserById(linkedAccount.getDiscordUserId()).getAsMention())).setActionRow(
                            Buttons.getSupportButton(event.getGuild())
                    ).queue();
                }
                return;
            }
        } else {
            if(!linkedAccount.isVisibleToPublic() && (linkedAccount.getDiscordUserId() != event.getUser().getIdLong())) {
                event.getHook().editOriginalEmbeds(ErrorMessages.getPrivate(event.getGuild(), event.getUser())).setActionRow(
                        Buttons.getSupportButton(event.getGuild())
                ).queue();
                return;
            }
        }

        try {
            event.getHook().editOriginalEmbeds(ReplyMessages.getStats(event.getGuild(), linkedAccount, riotAccount)).queue();
        } catch (InvalidRegionException e) {
            event.getHook().editOriginalEmbeds(ErrorMessages.getInvalidRegion(event.getGuild(), event.getUser(), riotAccount)).setActionRow(
                    Buttons.getSupportButton(event.getGuild())
            ).queue();
        }
    }

    @Override
    public CommandData getCommandData() {
        SubcommandData userSub = new SubcommandData("user", LanguageManager.getLanguage().getTranslation("command-stats-user-description"))
                .addOption(OptionType.USER, "user", "Discord user", true);
        SubcommandData riotIdSub = new SubcommandData("riot-id", LanguageManager.getLanguage().getTranslation("command-stats-riotid-description"))
                .addOption(OptionType.STRING, "name", "The name of the Riot-ID (<name>#<tag>)", true)
                .addOption(OptionType.STRING, "tag", "The tag of the Riot-ID (<name>#<tag>)", true);
        SubcommandData meSub = new SubcommandData("me", LanguageManager.getLanguage().getTranslation("command-stats-me-description"));
        return Commands.slash(getName(), getDescription()).addSubcommands(userSub, riotIdSub, meSub);
    }

    @Override
    public String getName() {
        return "stats";
    }

    @Override
    public String getDescription() {
        return "Get general information about a VALORANT profile";
    }

}
