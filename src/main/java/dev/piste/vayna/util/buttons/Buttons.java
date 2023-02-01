package dev.piste.vayna.util.buttons;

import dev.piste.vayna.buttons.DisconnectButton;
import dev.piste.vayna.buttons.VisibilityButton;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.util.Emoji;
import dev.piste.vayna.util.TranslationManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Buttons {

    public static Button getSupportButton(Guild guild) {
        TranslationManager manager = TranslationManager.getTranslation(guild);
        return Button.link(Configs.getSettings().getSupportGuild().getInviteUri(), manager.getTranslation("button-support")).withEmoji(Emoji.getDiscord());
    }

    public static Button getConnectButton(Guild guild, String authKey) {
        TranslationManager manager = TranslationManager.getTranslation(guild);
        return Button.link(Configs.getSettings().getWebsiteUri() + "/RSO/redirect/?authKey=" + authKey, manager.getTranslation("button-connect")).withEmoji(Emoji.getRiotGames());
    }

    public static Button getDisconnectButton(Guild guild) {
        TranslationManager manager = TranslationManager.getTranslation(guild);
        return Button.danger(new DisconnectButton().getName(), manager.getTranslation("button-disconnect"));
    }

    public static Button getVisibilityButton(Guild guild, boolean visibleToPublic) {
        TranslationManager manager = TranslationManager.getTranslation(guild);
        if(visibleToPublic) {
            return Button.secondary(new VisibilityButton().getName() + "private", manager.getTranslation("button-visibility")).withEmoji(net.dv8tion.jda.api.entities.emoji.Emoji.fromUnicode("\uD83D\uDD12"));
        } else {
            return Button.secondary(new VisibilityButton().getName() + "public", manager.getTranslation("button-visibility")).withEmoji(net.dv8tion.jda.api.entities.emoji.Emoji.fromUnicode("\uD83D\uDD13"));
        }
    }

}
