package dev.piste.vayna.listener;

import dev.piste.vayna.manager.StringSelectMenuManager;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Piste | https://github.com/zPiste
 */
public class StringSelectInteractionListener extends ListenerAdapter {

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        StringSelectMenuManager.perform(event);
    }

}
