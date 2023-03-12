package dev.piste.vayna.interactions.buttons;

import dev.piste.vayna.interactions.general.ConnectionInteraction;
import dev.piste.vayna.interactions.util.interfaces.IButton;
import dev.piste.vayna.mongodb.RSOConnection;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.util.StatsCounter;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class DisconnectButton implements IButton {

    public void perform(ButtonInteractionEvent event, String[] args, Language language) {
        event.deferEdit().queue();

        RSOConnection rsoConnection = new RSOConnection(event.getUser().getIdLong());
        if(rsoConnection.isExisting()) rsoConnection.delete();

        ConnectionInteraction.sendConnectionMissingMessage(event.getHook(), language);

        StatsCounter.countConnections();
    }

    @Override
    public String getName() {
        return "disconnect";
    }

}
