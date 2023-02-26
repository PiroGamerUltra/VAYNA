package dev.piste.vayna.interactions.managers;

import dev.piste.vayna.apis.HttpErrorException;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

/**
 * @author Piste | https://github.com/PisteDev
 */
public interface Button {

    void perform(ButtonInteractionEvent event, String[] args) throws HttpErrorException;

    String getName();

}
