package dev.piste.vayna.config.translations;

import dev.piste.vayna.config.Configs;
import dev.piste.vayna.config.translations.error.StatusCodeError;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

/**
 * @author Piste | https://github.com/zPiste
 */
// GSON CLASS
@SuppressWarnings("ALL")
public class Errors {

    private String supportButton;
    private StatusCodeError api;
    private StatusCodeError rateLimit;

    public StatusCodeError getApi() {
        return api;
    }

    public StatusCodeError getRateLimit() {
        return rateLimit;
    }

    public Button getSupportButton() {
         return Button.link(Configs.getSettings().getSupportGuild().getInviteUri(), supportButton).withEmoji(Emoji.getDiscord());
    }

}