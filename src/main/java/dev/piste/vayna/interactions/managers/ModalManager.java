package dev.piste.vayna.interactions.managers;

import dev.piste.vayna.interactions.modals.FeedbackModal;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

import java.util.HashMap;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class ModalManager {

    private static final HashMap<String, Modal> modals = new HashMap<>();

    public static void registerModals() {
        addModal(new FeedbackModal());
    }

    private static void addModal(Modal modal) {
        modals.put(modal.getName(), modal);
    }

    public static void perform(ModalInteractionEvent event) {
        Thread thread = new Thread(() -> modals.get(event.getModalId()).perform(event));
        thread.start();
    }

}
