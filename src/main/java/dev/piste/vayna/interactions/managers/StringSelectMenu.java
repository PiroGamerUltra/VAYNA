package dev.piste.vayna.interactions.managers;

import dev.piste.vayna.apis.HttpErrorException;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public interface StringSelectMenu {

    void perform(StringSelectInteractionEvent event) throws HttpErrorException, IOException, InterruptedException;

    String getName();

}
