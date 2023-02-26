package dev.piste.vayna.interactions.buttons;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.interactions.managers.Button;
import dev.piste.vayna.mongodb.AuthKey;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.ReplyMessages;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class VisibilityButton implements Button {

    public void perform(ButtonInteractionEvent event, String arg) throws HttpErrorException {
        LinkedAccount linkedAccount = new LinkedAccount(event.getUser().getIdLong());
        if(!linkedAccount.isExisting()) {
            event.editMessageEmbeds(ReplyMessages.getConnectionNone(event.getGuild(), event.getUser())).setActionRow(
                    Buttons.getConnectButton(event.getGuild(), new AuthKey(event.getUser().getIdLong()).getAuthKey())
            ).queue();
        } else {
            linkedAccount.setVisibleToPublic(arg.equalsIgnoreCase("public")).update();
            event.editMessageEmbeds(ReplyMessages.getConnectionPresent(event.getGuild(), event.getUser(), new RiotAPI().getAccount(linkedAccount.getRiotPuuid()).getRiotId(), linkedAccount.isVisibleToPublic())).setActionRow(
                    Buttons.getDisconnectButton(event.getGuild()),
                    Buttons.getVisibilityButton(event.getGuild(), linkedAccount.isVisibleToPublic())
            ).queue();
        }
    }

    @Override
    public String getName() {
        return "connection-visibility;";
    }

}
