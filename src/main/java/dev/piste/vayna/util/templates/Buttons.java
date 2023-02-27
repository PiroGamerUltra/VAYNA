package dev.piste.vayna.util.templates;

import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.interactions.buttons.DisconnectButton;
import dev.piste.vayna.interactions.buttons.HistoryButton;
import dev.piste.vayna.interactions.buttons.VisibilityButton;
import dev.piste.vayna.util.Emojis;
import dev.piste.vayna.util.translations.Language;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Buttons {

    public static Button getSupportButton(Language language) {
        return Button.link(ConfigManager.getSettingsConfig().getSupportGuildInviteUri(), language.getTranslation("button-support")).withEmoji(Emojis.getDiscord());
    }

    public static Button getConnectButton(Language language, String authKey) {
        return Button.link(ConfigManager.getSettingsConfig().getWebsiteUri() + "/RSO/redirect/?authKey=" + authKey, language.getTranslation("button-connect")).withEmoji(Emojis.getRiotGames());
    }

    public static Button getDisconnectButton(Language language) {
        return Button.danger(new DisconnectButton().getName(), language.getTranslation("button-disconnect"));
    }

    public static Button getVisibilityButton(Language language, boolean visibleToPublic) {
        if(visibleToPublic) {
            return Button.secondary(new VisibilityButton().getName() + ";private", language.getTranslation("button-visibility")).withEmoji(Emoji.fromUnicode("\uD83D\uDD12"));
        } else {
            return Button.secondary(new VisibilityButton().getName() + ";public", language.getTranslation("button-visibility")).withEmoji(Emoji.fromUnicode("\uD83D\uDD13"));
        }
    }

    public static Button getHistoryButton(Language language, RiotAccount riotAccount) {
        return Button.secondary(new HistoryButton().getName() + ";" + riotAccount.getPuuid(), language.getTranslation(";button-history"));
    }

}
