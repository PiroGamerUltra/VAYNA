package dev.piste.vayna.interactions.buttons;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.mongodb.RsoAuthKey;
import dev.piste.vayna.mongodb.RsoConnection;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.MessageEmbeds;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class VisibilityButton implements Button {

    public void perform(ButtonInteractionEvent event, String[] args) throws HttpErrorException, IOException, InterruptedException {
        Language language = LanguageManager.getLanguage(event.getGuild());

        RsoConnection rsoConnection = new RsoConnection(event.getUser().getIdLong());
        if(!rsoConnection.isExisting()) {
            RsoAuthKey rsoAuthKey = new RsoAuthKey(event.getUser().getIdLong());
            rsoAuthKey.refreshExpirationDate();
            event.editMessageEmbeds(MessageEmbeds.getNoConnectionEmbed(language, event.getUser(), rsoAuthKey.getExpirationDate())).setActionRow(
                    Buttons.getConnectButton(language, rsoAuthKey.getRsoAuthKey())
            ).queue();
        } else {
            rsoConnection.setVisibleToPublic(args[0].equalsIgnoreCase("public")).update();
            event.editMessageEmbeds(MessageEmbeds.getPresentConnectionEmbed(language, event.getUser(), new RiotAPI().getAccount(rsoConnection.getRiotPuuid()).getRiotId(), rsoConnection.isPubliclyVisible())).setActionRow(
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
