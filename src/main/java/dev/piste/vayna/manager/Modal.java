package dev.piste.vayna.manager;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

/**
 * @author Piste | https://github.com/zPiste
 */
public interface Modal {

    void perform(ModalInteractionEvent event);

    String getName();

}
