package dev.piste.vayna.buttons;

import dev.piste.vayna.api.riotgames.RiotAccount;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.embeds.ConnectionEmbed;
import dev.piste.vayna.mongodb.AuthKey;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class ChangeVisibilityButton {

    public static void performButton(ButtonInteractionEvent event) {

        LinkedAccount linkedAccount = new LinkedAccount(event.getUser().getIdLong());

        if(!linkedAccount.isExisting()) {
            String authKey = new AuthKey(event.getUser().getIdLong()).getAuthKey();

            event.replyEmbeds(ConnectionEmbed.getNoConnectionPresent(event.getUser().getAsMention())).setActionRow(
                    Button.link(Configs.getSettings().getWebsiteUri() + "/RSO/redirect/?authKey=" + authKey, "Connect").withEmoji(Emoji.getRiotGames())
            ).setEphemeral(true).queue();
        } else {
            RiotAccount riotAccount = RiotAccount.getByPuuid(linkedAccount.getRiotPuuid());

            linkedAccount.delete();

            switch (event.getButton().getId()) {
                case "connection;public" -> {

                    LinkedAccount.insert(linkedAccount.getDiscordUserId(), linkedAccount.getRiotPuuid(), true);

                    event.replyEmbeds(ConnectionEmbed.getConnectionPresent(riotAccount.getRiotId(), event.getUser().getAsMention(), true)).setActionRow(
                            Button.danger("connection;disconnect", "Disconnect"),
                            Button.secondary("connection;private", "Change visibility").withEmoji(net.dv8tion.jda.api.entities.emoji.Emoji.fromUnicode("\uD83D\uDD12"))
                    ).setEphemeral(true).queue();
                }
                case "connection;private" -> {

                    LinkedAccount.insert(linkedAccount.getDiscordUserId(), linkedAccount.getRiotPuuid(), false);

                    event.replyEmbeds(ConnectionEmbed.getConnectionPresent(riotAccount.getRiotId(), event.getUser().getAsMention(), false)).setActionRow(
                            Button.danger("connection;disconnect", "Disconnect"),
                            Button.secondary("connection;public", "Change visibility").withEmoji(net.dv8tion.jda.api.entities.emoji.Emoji.fromUnicode("\uD83D\uDD13"))
                    ).setEphemeral(true).queue();
                }
            }


        }

    }

}
