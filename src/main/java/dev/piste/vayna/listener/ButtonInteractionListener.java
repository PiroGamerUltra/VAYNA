package dev.piste.vayna.listener;

import dev.piste.vayna.buttons.DisconnectRiotAccount;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ButtonInteractionListener extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        switch (event.getButton().getId()) {
            case "/connection;disconnect" -> DisconnectRiotAccount.performButton(event);
        }
    }

}
