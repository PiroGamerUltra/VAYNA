package dev.piste.vayna.listener;

import dev.piste.vayna.buttons.ChangeVisibilityButton;
import dev.piste.vayna.buttons.DisconnectRiotAccountButton;
import dev.piste.vayna.buttons.RankButton;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ButtonInteractionListener extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String[] args = event.getButton().getId().split(";");
        switch (args[0]) {
            case "disconnect" -> DisconnectRiotAccountButton.perform(event);
            case "change-visibility" -> ChangeVisibilityButton.perform(event);
            case "rank" -> new RankButton().perform(event);
        }
    }

}
