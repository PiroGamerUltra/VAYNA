package dev.piste.vayna.buttons;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.riotgames.RiotAPI;
import dev.piste.vayna.apis.riotgames.gson.RiotAccount;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.embeds.ConnectionEmbed;
import dev.piste.vayna.mongodb.AuthKey;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class ChangeVisibilityButton {

    public static void perform(ButtonInteractionEvent event) throws StatusCodeException {
        event.deferReply().setEphemeral(true).queue();

        LinkedAccount linkedAccount = new LinkedAccount(event.getUser().getIdLong());

        if(!linkedAccount.isExisting()) {
            event.getHook().editOriginalEmbeds(ConnectionEmbed.getNoConnectionPresent(event.getUser().getAsMention())).setActionRow(
                    Button.link(Configs.getSettings().getWebsiteUri() + "/RSO/redirect/?authKey=" + new AuthKey(event.getUser().getIdLong()).getAuthKey(), "Connect")
                            .withEmoji(Emoji.getRiotGames())
            ).queue();
        } else {
            RiotAccount riotAccount = RiotAPI.getAccountByPuuid(linkedAccount.getRiotPuuid());

            linkedAccount.delete();

            String[] args = event.getButton().getId().split(";");

            switch (args[1]) {
                case "public" -> {

                    LinkedAccount.insert(linkedAccount.getDiscordUserId(), linkedAccount.getRiotPuuid(), true);

                    event.getHook().editOriginalEmbeds(ConnectionEmbed.getConnectionPresent(riotAccount.getRiotId(), event.getUser().getAsMention(), true)).setActionRow(
                            Button.danger("disconnect", "Disconnect"),
                            Button.secondary("change-visibility;private", "Change visibility").withEmoji(net.dv8tion.jda.api.entities.emoji.Emoji.fromUnicode("\uD83D\uDD12"))
                    ).queue();
                }
                case "private" -> {

                    LinkedAccount.insert(linkedAccount.getDiscordUserId(), linkedAccount.getRiotPuuid(), false);

                    event.getHook().editOriginalEmbeds(ConnectionEmbed.getConnectionPresent(riotAccount.getRiotId(), event.getUser().getAsMention(), false)).setActionRow(
                            Button.danger("disconnect", "Disconnect"),
                            Button.secondary("change-visibility;public", "Change visibility").withEmoji(net.dv8tion.jda.api.entities.emoji.Emoji.fromUnicode("\uD83D\uDD13"))
                    ).queue();
                }
            }


        }

    }

}
