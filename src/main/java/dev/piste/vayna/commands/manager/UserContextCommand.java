package dev.piste.vayna.commands.manager;

import dev.piste.vayna.apis.StatusCodeException;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;

/**
 * @author Piste | https://github.com/zPiste
 */
public interface UserContextCommand {

    void perform(UserContextInteractionEvent event) throws StatusCodeException;

    String getName();

    void register();

}
