package dev.piste.vayna.util.templates;

import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.interactions.buttons.DisconnectButton;
import dev.piste.vayna.interactions.buttons.HistoryButton;
import dev.piste.vayna.interactions.buttons.VisibilityButton;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.util.Emoji;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class Buttons {

    public static Button getSupportButton(Guild guild) {
        return Button.link(ConfigManager.getSettingsConfig().getSupportGuildInviteUri(), LanguageManager.getLanguage(guild).getTranslation("button-support")).withEmoji(Emoji.getDiscord());
    }

    public static Button getConnectButton(Guild guild, String authKey) {
        return Button.link(ConfigManager.getSettingsConfig().getWebsiteUri() + "/RSO/redirect/?authKey=" + authKey, LanguageManager.getLanguage(guild).getTranslation("button-connect")).withEmoji(Emoji.getRiotGames());
    }

    public static Button getDisconnectButton(Guild guild) {
        return Button.danger(new DisconnectButton().getName(), LanguageManager.getLanguage(guild).getTranslation("button-disconnect"));
    }

    public static Button getVisibilityButton(Guild guild, boolean visibleToPublic) {
        if(visibleToPublic) {
            return Button.secondary(new VisibilityButton().getName() + "private", LanguageManager.getLanguage(guild).getTranslation("button-visibility")).withEmoji(net.dv8tion.jda.api.entities.emoji.Emoji.fromUnicode("\uD83D\uDD12"));
        } else {
            return Button.secondary(new VisibilityButton().getName() + "public", LanguageManager.getLanguage(guild).getTranslation("button-visibility")).withEmoji(net.dv8tion.jda.api.entities.emoji.Emoji.fromUnicode("\uD83D\uDD13"));
        }
    }

    public static Button getHistoryButton(Guild guild, RiotAccount riotAccount) {
        return Button.secondary(new HistoryButton().getName() + riotAccount.getPuuid(), LanguageManager.getLanguage(guild).getTranslation("button-history"));
    }

}
