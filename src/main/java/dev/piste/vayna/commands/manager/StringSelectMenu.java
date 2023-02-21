package dev.piste.vayna.commands.manager;

import dev.piste.vayna.apis.StatusCodeException;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

/**
 * @author Piste | https://github.com/zPiste
 */
public interface StringSelectMenu {

    void perform(StringSelectInteractionEvent event) throws StatusCodeException;

    String getName();

}
