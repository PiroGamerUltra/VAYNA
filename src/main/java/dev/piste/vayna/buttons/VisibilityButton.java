package dev.piste.vayna.buttons;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.riotgames.RiotAPI;
import dev.piste.vayna.manager.Button;
import dev.piste.vayna.mongodb.AuthKey;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.buttons.Buttons;
import dev.piste.vayna.util.messages.ReplyMessages;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class VisibilityButton implements Button {

    public void perform(ButtonInteractionEvent event, String arg) throws StatusCodeException {
        event.deferReply().setEphemeral(true).queue();

        LinkedAccount linkedAccount = new LinkedAccount(event.getUser().getIdLong());
        if(!linkedAccount.isExisting()) {
            event.getHook().editOriginalEmbeds(ReplyMessages.getConnectionNone(event.getGuild(), event.getUser())).setActionRow(
                    Buttons.getConnectButton(event.getGuild(), new AuthKey(event.getUser().getIdLong()).getAuthKey())
            ).queue();
        } else {
            linkedAccount.update(arg.equalsIgnoreCase("public"));
            event.getHook().editOriginalEmbeds(ReplyMessages.getConnectionPresent(event.getGuild(), event.getUser(), RiotAPI.getAccountByPuuid(linkedAccount.getRiotPuuid()).getRiotId(), linkedAccount.isVisibleToPublic())).setActionRow(
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
