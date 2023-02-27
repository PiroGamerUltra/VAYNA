package dev.piste.vayna.interactions.commands.context;

import dev.piste.vayna.apis.HttpErrorException;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public interface UserContextCommand {

    void perform(UserContextInteractionEvent event) throws HttpErrorException, IOException, InterruptedException;

    CommandData getCommandData();

    String getName();
}
