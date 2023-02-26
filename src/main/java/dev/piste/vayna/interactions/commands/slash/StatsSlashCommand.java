package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.interactions.managers.SlashCommand;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emojis;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.ErrorMessages;
import dev.piste.vayna.util.templates.MessageEmbeds;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class StatsSlashCommand implements SlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event) throws HttpErrorException {
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
                    riotAccount = new RiotAPI().getAccount(linkedAccount.getRiotPuuid());
                }
            }
            // /stats user <@user>
            case "user" -> {
                linkedAccount = new LinkedAccount(event.getOption("user").getAsUser().getIdLong());
                if(linkedAccount.isExisting()) {
                    riotAccount = new RiotAPI().getAccount(linkedAccount.getRiotPuuid());
                }
            }
            // /stats riot-id <name> <tag>
            case "riot-id" -> {
                String gameName = event.getOption("name").getAsString();
                String tagLine = event.getOption("tag").getAsString();
                try {
                    riotAccount = new RiotAPI().getAccount(gameName, tagLine);
                    linkedAccount = new LinkedAccount(riotAccount.getPuuid());
                } catch (HttpErrorException e) {
                    if(e.getStatusCode() == 400 || e.getStatusCode() == 404) {
                        Embed embed = new Embed()
                                .setAuthor(event.getUser().getName(), event.getUser().getAvatarUrl())
                                .setColor(255, 0, 0)
                                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-stats-error-riotid-embed-title"))
                                .setDescription(language.getTranslation("command-stats-error-riotid-embed-description")
                                        .replaceAll("%emoji:riotgames%", Emojis.getRiotGames().getFormatted())
                                        .replaceAll("%riotid%", gameName + "#" + tagLine));
                        event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                                Buttons.getSupportButton(event.getGuild())
                        ).queue();
                        return;
                    } else {
                        throw e;
                    }
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
            event.getHook().editOriginalEmbeds(MessageEmbeds.getStatsEmbed(language, linkedAccount, riotAccount)).queue();
        } catch (HttpErrorException e) {
            if(e.getStatusCode() == 400 || e.getStatusCode() == 404) {
                event.getHook().editOriginalEmbeds(ErrorMessages.getInvalidRegion(event.getGuild(), event.getUser(), riotAccount)).setActionRow(
                        Buttons.getSupportButton(event.getGuild())
                ).queue();
            } else {
                throw e;
            }
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
