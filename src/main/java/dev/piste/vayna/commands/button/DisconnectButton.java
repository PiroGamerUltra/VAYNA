package dev.piste.vayna.commands.button;

import dev.piste.vayna.util.StatsCounter;
import dev.piste.vayna.commands.manager.Button;
import dev.piste.vayna.mongodb.AuthKey;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.ReplyMessages;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class DisconnectButton implements Button {

    public void perform(ButtonInteractionEvent event, String arg) {
        LinkedAccount linkedAccount = new LinkedAccount(event.getUser().getIdLong());
        if(linkedAccount.isExisting()) linkedAccount.delete();

        // Reply
        event.editMessageEmbeds(ReplyMessages.getConnectionNone(event.getGuild(), event.getUser())).setActionRow(
                Buttons.getConnectButton(event.getGuild(), new AuthKey(event.getUser().getIdLong()).getAuthKey())
        ).queue();

        StatsCounter.countConnections();
    }

    @Override
    public String getName() {
        return "disconnect;";
    }

}
