package dev.piste.vayna.manager;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.buttons.DisconnectButton;
import dev.piste.vayna.buttons.RankButton;
import dev.piste.vayna.buttons.VisibilityButton;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.config.translations.Language;
import dev.piste.vayna.modals.FeedbackModal;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.util.HashMap;

/**
 * @author Piste | https://github.com/zPiste
 */
public class ModalManager {

    private static final HashMap<String, Modal> modals = new HashMap<>();

    public static void registerModals() {
        addButton(new FeedbackModal());
    }

    private static void addButton(Modal modal) {
        modals.put(modal.getName(), modal);
    }

    public static void perform(ModalInteractionEvent event) {
        Thread thread = new Thread(() -> {
            modals.get(event.getModalId()).perform(event);
        });
        thread.start();
    }

}
