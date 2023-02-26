package dev.piste.vayna.interactions.managers;

import dev.piste.vayna.apis.HttpErrorException;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;

/**
 * @author Piste | https://github.com/PisteDev
 */
public interface UserContextCommand {

    void perform(UserContextInteractionEvent event) throws HttpErrorException;

    String getName();

    void register();

}
