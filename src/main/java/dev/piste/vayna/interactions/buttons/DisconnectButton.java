package dev.piste.vayna.interactions.buttons;

import dev.piste.vayna.util.StatsCounter;
import dev.piste.vayna.mongodb.RsoAuthKey;
import dev.piste.vayna.mongodb.RsoConnection;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.MessageEmbeds;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class DisconnectButton implements Button {

    public void perform(ButtonInteractionEvent event, String[] args) {
        Language language = LanguageManager.getLanguage(event.getGuild());
        RsoConnection rsoConnection = new RsoConnection(event.getUser().getIdLong());
        if(rsoConnection.isExisting()) rsoConnection.delete();

        // Reply
        RsoAuthKey rsoAuthKey = new RsoAuthKey(event.getUser().getIdLong());
        rsoAuthKey.refreshExpirationDate();
        event.editMessageEmbeds(MessageEmbeds.getNoConnectionEmbed(language, event.getUser(), rsoAuthKey.getExpirationDate())).setActionRow(
                Buttons.getConnectButton(language, rsoAuthKey.getRsoAuthKey())
        ).queue();

        StatsCounter.countConnections();
    }

    @Override
    public String getName() {
        return "disconnect";
    }

}
