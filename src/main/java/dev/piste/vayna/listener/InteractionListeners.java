package dev.piste.vayna.listener;

import dev.piste.vayna.interactions.InteractionManager;
import dev.piste.vayna.translations.LanguageManager;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.jetbrains.annotations.NotNull;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class InteractionListeners extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        performInteraction(event, event.getHook());
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        performInteraction(event, event.getHook());
    }

    @Override
    public void onUserContextInteraction(@NotNull UserContextInteractionEvent event) {
        performInteraction(event, event.getHook());
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        performInteraction(event, event.getHook());
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        performInteraction(event, event.getHook());
    }

    private void performInteraction(GenericEvent event, InteractionHook hook) {
        Thread interactionThread = new Thread(() -> InteractionManager.perform(event, hook, LanguageManager.getLanguage(hook.getInteraction().getGuild())));
        interactionThread.setName("InteractionEvent");
        interactionThread.start();
    }

}