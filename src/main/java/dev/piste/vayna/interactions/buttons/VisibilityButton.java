package dev.piste.vayna.interactions.buttons;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.RiotGamesAPI;
import dev.piste.vayna.mongodb.RsoAuthKey;
import dev.piste.vayna.mongodb.RsoConnection;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.MessageEmbeds;
import dev.piste.vayna.translations.Language;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class VisibilityButton implements IButton {

    public void perform(ButtonInteractionEvent event, String[] args, Language language) throws HttpErrorException, IOException, InterruptedException {
        RsoConnection rsoConnection = new RsoConnection(event.getUser().getIdLong());
        if(!rsoConnection.isExisting()) {
            RsoAuthKey rsoAuthKey = new RsoAuthKey(event.getUser().getIdLong());
            rsoAuthKey.refreshExpirationDate();
            event.editMessageEmbeds(MessageEmbeds.getNoConnectionEmbed(language, event.getUser(), rsoAuthKey.getExpirationDate())).setActionRow(
                    Buttons.getConnectButton(language, rsoAuthKey.getAuthKey())
            ).queue();
        } else {
            rsoConnection.setVisibleToPublic(args[0].equalsIgnoreCase("public")).update();
            event.editMessageEmbeds(MessageEmbeds.getPresentConnectionEmbed(language, event.getUser(), new RiotGamesAPI().getAccount(rsoConnection.getRiotPuuid()).getRiotId(), rsoConnection.isPubliclyVisible())).setActionRow(
                    Buttons.getDisconnectButton(language),
                    Buttons.getVisibilityButton(language, rsoConnection.isPubliclyVisible())
            ).queue();
        }
    }

    @Override
    public String getName() {
        return "connectionVisibility";
    }

}
