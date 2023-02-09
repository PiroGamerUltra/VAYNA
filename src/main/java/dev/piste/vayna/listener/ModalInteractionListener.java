package dev.piste.vayna.listener;

import dev.piste.vayna.manager.ModalManager;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Piste | https://github.com/zPiste
 */
public class ModalInteractionListener extends ListenerAdapter {

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        ModalManager.perform(event);
    }

}
