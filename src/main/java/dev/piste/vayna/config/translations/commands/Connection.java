package dev.piste.vayna.config.translations.commands;

import dev.piste.vayna.buttons.VisibilityButton;
import dev.piste.vayna.buttons.DisconnectButton;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.config.translations.commands.connection.None;
import dev.piste.vayna.config.translations.commands.connection.Present;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Connection {

    private Present present;
    private None none;
    private String connectButton;
    private String disconnectButton;
    private String visibilityButton;

    public None getNone() {
        return none;
    }

    public Present getPresent() {
        return present;
    }

    public Button getConnectButton(String authKey) {
        return Button.link(Configs.getSettings().getWebsiteUri() + "/RSO/redirect/?authKey=" + authKey, connectButton).withEmoji(Emoji.getRiotGames());
    }

    public Button getDisconnectButton() {
        return Button.danger(new DisconnectButton().getName(), disconnectButton);
    }

    public Button getVisibilityButton(boolean visibleToPublic) {
        if(visibleToPublic) {
            return Button.secondary(new VisibilityButton().getName() + "private", visibilityButton).withEmoji(net.dv8tion.jda.api.entities.emoji.Emoji.fromUnicode("\uD83D\uDD12"));
        } else {
            return Button.secondary(new VisibilityButton().getName() + "public", visibilityButton).withEmoji(net.dv8tion.jda.api.entities.emoji.Emoji.fromUnicode("\uD83D\uDD13"));
        }

    }

}
