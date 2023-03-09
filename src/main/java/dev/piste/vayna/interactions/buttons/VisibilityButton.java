package dev.piste.vayna.interactions.buttons;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.interactions.ConnectionInteraction;
import dev.piste.vayna.interactions.util.interfaces.IButton;
import dev.piste.vayna.mongodb.RSOConnection;
import dev.piste.vayna.translations.Language;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class VisibilityButton implements IButton {

    public void perform(ButtonInteractionEvent event, String[] args, Language language) throws HttpErrorException, IOException, InterruptedException {
        event.deferEdit().queue();

        RSOConnection rsoConnection = new RSOConnection(event.getUser().getIdLong());
        if(!rsoConnection.isExisting()) {
            ConnectionInteraction.sendConnectionMissingMessage(event.getHook(), language);
        } else {
            rsoConnection.setVisibleToPublic(args[0].equalsIgnoreCase("public")).update();
            ConnectionInteraction.sendConnectionPresentMessage(rsoConnection, event.getHook(), language);
        }
    }

    @Override
    public String getName() {
        return "connectionVisibility";
    }

}
