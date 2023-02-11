package dev.piste.vayna.util.templates;

import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.commands.button.DisconnectButton;
import dev.piste.vayna.commands.button.RankButton;
import dev.piste.vayna.commands.button.VisibilityButton;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.manager.ButtonManager;
import dev.piste.vayna.util.Emoji;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.UUID;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Buttons {

    public static Button getSupportButton(Guild guild) {
        return Button.link(ConfigManager.getSettingsConfig().getSupportGuild().getInviteUri(), LanguageManager.getLanguage(guild).getTranslation("button-support")).withEmoji(Emoji.getDiscord());
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

    public static Button getRankButton(Guild guild, RiotAccount riotAccount) {
        String uuid = UUID.randomUUID().toString();
        ButtonManager.putInStatsButtonMap(uuid, riotAccount);
        return Button.secondary(new RankButton().getName() + uuid, LanguageManager.getLanguage(guild).getTranslation("button-rank")).withEmoji(Emoji.getRankByTierName(0));
    }



}
