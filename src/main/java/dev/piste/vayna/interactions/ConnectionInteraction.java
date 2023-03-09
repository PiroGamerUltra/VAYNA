package dev.piste.vayna.interactions;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.RiotGamesAPI;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.interactions.buttons.DisconnectButton;
import dev.piste.vayna.interactions.buttons.VisibilityButton;
import dev.piste.vayna.mongodb.RSOAuthKey;
import dev.piste.vayna.mongodb.RSOConnection;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emojis;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class ConnectionInteraction {

    public static void sendConnectionMissingMessage(InteractionHook hook, Language language) {
        RSOAuthKey rsoAuthKey = new RSOAuthKey(hook.getInteraction().getUser().getIdLong());
        rsoAuthKey.refreshExpirationDate();
        Embed embed = new Embed()
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("connection-embed-title"))
                .setDescription(language.getTranslation("connection-missing-embed-desc")
                        .replaceAll("%expirationDate%", "<t:" + Math.round((float) rsoAuthKey.getExpirationDate().getTime() / 1000) + ":R>"));

        hook.editOriginalEmbeds(embed.build()).setActionRow(
                Button.link(ConfigManager.getSettingsConfig().getWebsiteUri() + "/RSO/redirect/?authKey=" + rsoAuthKey.getAuthKey(), language.getTranslation("button-connect-name"))
                        .withEmoji(Emojis.getRiotGames())
        ).queue();
    }

    public static void sendConnectionPresentMessage(RSOConnection rsoConnection, InteractionHook hook, Language language) throws IOException, HttpErrorException, InterruptedException {
        String riotId = new RiotGamesAPI().getAccount(rsoConnection.getRiotPuuid()).getRiotId();
        Embed embed =  new Embed()
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("connection-embed-title"))
                .setDescription(language.getTranslation("connection-present-embed-desc"))
                .addField(language.getTranslation("connection-present-embed-field-1-name"),
                        hook.getInteraction().getUser().getAsMention() + " " + Emojis.getDiscord().getFormatted() + " \uD83D\uDD17 " + Emojis.getRiotGames().getFormatted() + " `" + riotId + "`", true)
                .addField(language.getTranslation("connection-present-embed-field-2-name"),
                        rsoConnection.isPubliclyVisible() ? "\uD83D\uDD13 " + language.getTranslation("connection-present-embed-field-2-text-public")
                                : "\uD83D\uDD12 " + language.getTranslation("connection-present-embed-field-2-text-private"), true);
        String buttonArg = rsoConnection.isPubliclyVisible() ? "private" : "public";
        String emojiUnicode = rsoConnection.isPubliclyVisible() ? "\uD83D\uDD12" : "\uD83D\uDD13";

        hook.editOriginalEmbeds(embed.build()).setActionRow(
                Button.danger(new DisconnectButton().getName(), language.getTranslation("button-disconnect-name")),
                Button.secondary(new VisibilityButton().getName() + ";" + buttonArg, language.getTranslation("button-visibility-name")).withEmoji(Emoji.fromUnicode(emojiUnicode))
        ).queue();
    }

}