package dev.piste.vayna.interactions.managers;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.interactions.buttons.DisconnectButton;
import dev.piste.vayna.interactions.buttons.HistoryButton;
import dev.piste.vayna.interactions.buttons.VisibilityButton;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class ButtonManager {

    private static final HashMap<String, Button> buttons = new HashMap<>();

    public static void registerButtons() {
        addButton(new DisconnectButton());
        addButton(new VisibilityButton());
        addButton(new HistoryButton());
    }

    private static void addButton(Button button) {
        buttons.put(button.getName(), button);
    }

    public static void perform(ButtonInteractionEvent event) throws HttpErrorException, IOException, InterruptedException {
        String buttonId = event.getButton().getId().split(";")[0];
        String[] args = event.getButton().getId().substring(buttonId.length()).split(";");
        buttons.get(buttonId).perform(event, args);
    }

}
