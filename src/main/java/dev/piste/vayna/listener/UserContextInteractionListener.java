package dev.piste.vayna.listener;

import dev.piste.vayna.manager.UserContextCommandManager;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Piste | https://github.com/zPiste
 */
public class UserContextInteractionListener extends ListenerAdapter {

    @Override
    public void onUserContextInteraction(@NotNull UserContextInteractionEvent event) {
        UserContextCommandManager.perform(event);
    }
}
