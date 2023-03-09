package dev.piste.vayna.interactions.commands.context;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.RiotGamesAPI;
import dev.piste.vayna.apis.entities.riotgames.RiotAccount;
import dev.piste.vayna.interactions.util.exceptions.InvalidRegionException;
import dev.piste.vayna.interactions.util.exceptions.RSOConnectionMissingException;
import dev.piste.vayna.interactions.util.exceptions.RSOConnectionPrivateException;
import dev.piste.vayna.interactions.util.interfaces.IUserContextCommand;
import dev.piste.vayna.interactions.StatsInteraction;
import dev.piste.vayna.mongodb.RSOConnection;
import dev.piste.vayna.translations.Language;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class StatsContextCommand implements IUserContextCommand {

    @Override
    public void perform(UserContextInteractionEvent event, Language language) throws HttpErrorException, IOException, InterruptedException, RSOConnectionPrivateException, InvalidRegionException, RSOConnectionMissingException {
        event.deferReply(false).queue();

        RSOConnection rsoConnection = new RSOConnection(event.getTarget().getIdLong());
        RiotAccount riotAccount = null;
        if(rsoConnection.isExisting()) {
            riotAccount = new RiotGamesAPI().getAccount(rsoConnection.getRiotPuuid());
        }

        StatsInteraction.sendStatsEmbed(riotAccount, rsoConnection, event.getHook(), language);
    }

    @Override
    public CommandData getCommandData() {
        return Commands.user(getDisplayName());
    }

    @Override
    public String getDisplayName() {
        return "Stats";
    }

}