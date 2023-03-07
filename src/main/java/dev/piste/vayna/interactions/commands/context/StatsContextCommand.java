package dev.piste.vayna.interactions.commands.context;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.interactions.StatsInteraction;
import dev.piste.vayna.mongodb.RsoConnection;
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
    public void perform(UserContextInteractionEvent event, Language language) throws HttpErrorException, IOException, InterruptedException {
        event.deferReply(false).queue();

        RsoConnection rsoConnection = new RsoConnection(event.getTarget().getIdLong());
        RiotAccount riotAccount = null;
        if(rsoConnection.isExisting()) {
            riotAccount = new RiotAPI().getAccount(rsoConnection.getRiotPuuid());
        }

        new StatsInteraction().perform(event.getUser(), riotAccount, rsoConnection, event.getHook(), language);
    }

    @Override
    public CommandData getCommandData() {
        return Commands.user(getName());
    }

    @Override
    public String getName() {
        return "Stats";
    }
}
