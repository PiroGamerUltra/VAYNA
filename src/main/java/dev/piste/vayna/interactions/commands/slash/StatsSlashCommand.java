package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.mongodb.RsoConnection;
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

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class StatsSlashCommand implements SlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event) throws HttpErrorException, IOException, InterruptedException {
        event.deferReply().queue();

        Language language = LanguageManager.getLanguage(event.getGuild());

        RsoConnection rsoConnection = null;
        RiotAccount riotAccount = null;
        if(event.getSubcommandName() == null) return;
        switch (event.getSubcommandName()) {
            // /stats me
            case "me" -> {
                rsoConnection = new RsoConnection(event.getUser().getIdLong());
                if(rsoConnection.isExisting()) {
                    riotAccount = new RiotAPI().getAccount(rsoConnection.getRiotPuuid());
                }
            }
            // /stats user <@user>
            case "user" -> {
                rsoConnection = new RsoConnection(event.getOption("user").getAsUser().getIdLong());
                if(rsoConnection.isExisting()) {
                    riotAccount = new RiotAPI().getAccount(rsoConnection.getRiotPuuid());
                }
            }
            // /stats riot-id <name> <tag>
            case "riot-id" -> {
                String gameName = event.getOption("name").getAsString();
                String tagLine = event.getOption("tag").getAsString();
                try {
                    riotAccount = new RiotAPI().getAccount(gameName, tagLine);
                    rsoConnection = new RsoConnection(riotAccount.getPuuid());
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
                                Buttons.getSupportButton(language)
                        ).queue();
                        return;
                    } else {
                        throw e;
                    }
                }
            }
        }

        if(!rsoConnection.isExisting()) {
            if(rsoConnection.getDiscordUserId() != 0) {
                if (rsoConnection.getDiscordUserId() == event.getUser().getIdLong()) {
                    event.getHook().editOriginalEmbeds(ErrorMessages.getNoConnectionSelf(event.getGuild(), event.getUser())).setActionRow(
                            Buttons.getSupportButton(language)
                    ).queue();
                } else {
                    event.getHook().editOriginalEmbeds(ErrorMessages.getNoConnection(event.getGuild(), event.getUser(), event.getJDA().getUserById(rsoConnection.getDiscordUserId()).getAsMention())).setActionRow(
                            Buttons.getSupportButton(language)
                    ).queue();
                }
                return;
            }
        } else {
            if(!rsoConnection.isPubliclyVisible() && (rsoConnection.getDiscordUserId() != event.getUser().getIdLong())) {
                event.getHook().editOriginalEmbeds(ErrorMessages.getPrivate(event.getGuild(), event.getUser())).setActionRow(
                        Buttons.getSupportButton(language)
                ).queue();
                return;
            }
        }

        try {
            event.getHook().editOriginalEmbeds(MessageEmbeds.getStatsEmbed(language, rsoConnection, riotAccount)).queue();
        } catch (HttpErrorException e) {
            if(e.getStatusCode() == 400 || e.getStatusCode() == 404) {
                event.getHook().editOriginalEmbeds(ErrorMessages.getInvalidRegion(event.getGuild(), event.getUser(), riotAccount)).setActionRow(
                        Buttons.getSupportButton(language)
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
