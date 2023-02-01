package dev.piste.vayna.buttons;

import dev.piste.vayna.config.translations.Language;
import dev.piste.vayna.counter.StatsCounter;
import dev.piste.vayna.manager.Button;
import dev.piste.vayna.mongodb.AuthKey;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.buttons.Buttons;
import dev.piste.vayna.util.messages.ReplyMessages;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class DisconnectButton implements Button {

    public void perform(ButtonInteractionEvent event, String arg) {
        event.deferReply().setEphemeral(true).queue();

        StatsCounter.countConnections();

        LinkedAccount linkedAccount = new LinkedAccount(event.getUser().getIdLong());
        if(linkedAccount.isExisting()) linkedAccount.delete();

        event.getHook().editOriginalEmbeds(ReplyMessages.getConnectionNone(event.getGuild(), event.getUser())).setActionRow(
                Buttons.getConnectButton(event.getGuild(), new AuthKey(event.getUser().getIdLong()).getAuthKey())
        ).queue();
    }

    @Override
    public String getName() {
        return "disconnect;";
    }

}
