package dev.piste.vayna.buttons;

import dev.piste.vayna.commands.ConnectionCommand;
import dev.piste.vayna.config.SettingsConfig;
import dev.piste.vayna.embeds.ConnectionEmbed;
import dev.piste.vayna.mongodb.AuthKey;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class DisconnectRiotAccount {

    public static void performButton(ButtonInteractionEvent event) {

        LinkedAccount linkedAccount = new LinkedAccount(event.getUser().getIdLong());

        ConnectionCommand.countConnections();

        if(linkedAccount.isExisting()) {
            // The account has been found in the database
            linkedAccount.delete();
        }

        String authKey = new AuthKey(event.getUser().getIdLong()).getAuthKey();

        event.replyEmbeds(ConnectionEmbed.getNoConnectionPresent(event.getUser().getAsTag())).setActionRow(
                Button.link(SettingsConfig.getWebsiteUri() + "/RSO/redirect/?authKey=" + authKey, "Connect").withEmoji(Emoji.getRiotGames())
        ).setEphemeral(true).queue();
    }

}
