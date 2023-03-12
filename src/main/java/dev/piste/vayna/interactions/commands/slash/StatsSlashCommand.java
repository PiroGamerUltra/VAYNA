package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.http.HttpErrorException;
import dev.piste.vayna.http.apis.RiotGamesAPI;
import dev.piste.vayna.http.models.riotgames.RiotAccount;
import dev.piste.vayna.interactions.util.exceptions.*;
import dev.piste.vayna.interactions.util.interfaces.ISlashCommand;
import dev.piste.vayna.interactions.general.StatsInteraction;
import dev.piste.vayna.mongodb.RSOConnection;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.translations.LanguageManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class StatsSlashCommand implements ISlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event, Language language) throws HttpErrorException, IOException, InterruptedException, InvalidRiotIdException, RSOConnectionPrivateException, InvalidRegionException, RSOConnectionMissingException {
        event.deferReply(false).queue();

        RSOConnection rsoConnection = null;
        RiotAccount riotAccount = null;
        if(event.getSubcommandName() == null) return;
        switch (event.getSubcommandName()) {
            // /stats me
            case "me" -> {
                rsoConnection = new RSOConnection(event.getUser().getIdLong());
                if(rsoConnection.isExisting()) {
                    riotAccount = new RiotGamesAPI().getAccount(rsoConnection.getRiotPuuid());
                }
            }
            // /stats user <@user>
            case "user" -> {
                rsoConnection = new RSOConnection(event.getOption("user").getAsUser().getIdLong());
                if(rsoConnection.isExisting()) {
                    riotAccount = new RiotGamesAPI().getAccount(rsoConnection.getRiotPuuid());
                }
            }
            // /stats riot-id <name> <tag>
            case "riot-id" -> {
                String name = event.getOption("name").getAsString();
                String tag = event.getOption("tag").getAsString();
                try {
                    riotAccount = new RiotGamesAPI().getAccount(name, tag);
                    rsoConnection = new RSOConnection(riotAccount.getPUUID());
                } catch (HttpErrorException e) {
                    if(e.getStatusCode() == 400 || e.getStatusCode() == 404) {
                        throw new InvalidRiotIdException(name, tag);
                    } else {
                        throw e;
                    }
                }
            }
        }

        StatsInteraction.sendStatsEmbed(riotAccount, rsoConnection, event.getHook(), language);
    }

    @Override
    public String getName() {
        return "stats";
    }

    @Override
    public String getDescription(Language language) {
        return language.getTranslation("command-stats-desc");
    }

    @Override
    public CommandData getCommandData() {
        SubcommandData userSub = new SubcommandData("user", LanguageManager.getDefaultLanguage().getTranslation("command-stats-user-desc"))
                .addOption(OptionType.USER, "user", "Discord user", true);
        SubcommandData riotIdSub = new SubcommandData("riot-id", LanguageManager.getDefaultLanguage().getTranslation("command-stats-riotid-desc"))
                .addOption(OptionType.STRING, "name", "The name of the Riot-ID (<name>#<tag>)", true)
                .addOption(OptionType.STRING, "tag", "The tag of the Riot-ID (<name>#<tag>)", true);
        SubcommandData meSub = new SubcommandData("me", LanguageManager.getDefaultLanguage().getTranslation("command-stats-me-desc"));
        return Commands.slash(getName(), getDescription(LanguageManager.getDefaultLanguage())).addSubcommands(userSub, riotIdSub, meSub);
    }

}