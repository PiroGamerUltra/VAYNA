package dev.piste.vayna.util.messages;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.TranslationManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

/**
 * @author Piste | https://github.com/zPiste
 */
public class ErrorMessages {

    public static Embed getStatusCodeErrorEmbed(Guild guild, User user, StatusCodeException exception) {
        String[] message = exception.getMessage().split(" ");
        int statusCode = Integer.parseInt(message[0]);

        TranslationManager manager = TranslationManager.getTranslation(guild);

        Embed embed = new Embed()
                .setColor(255, 0, 0)
                .setTitle(manager.getTranslation("embed-title-prefix") + manager.getTranslation("error-api-embed-title"))
                .setDescription(manager.getTranslation("error-api-embed-description"))
                .setAuthor(user.getName(), Configs.getSettings().getWebsiteUri(), user.getAvatarUrl());
        switch (statusCode) {
            case 400 -> {
                embed.addField(manager.getTranslation("error-api-embed-field-1-name"), "`400: Bad request`", false);
            }
            case 401 -> {
                embed.addField(manager.getTranslation("error-api-embed-field-1-name"), "`401: Unauthorized`", false);
            }
            case 403 -> {
                embed.addField(manager.getTranslation("error-api-embed-field-1-name"), "`403: Forbidden`", false);
            }
            case 404 -> {
                embed.addField(manager.getTranslation("error-api-embed-field-1-name"), "`404: Not found`", false);
            }
            case 405 -> {
                embed.addField(manager.getTranslation("error-api-embed-field-1-name"), "`405: Method not allowed`", false);
            }
            case 408 -> {
                embed.addField(manager.getTranslation("error-api-embed-field-1-name"), "`408: Timeout`", false);
            }
            case 500 -> {
                embed.addField(manager.getTranslation("error-api-embed-field-1-name"), "`500: Internal server error`", false);
            }
            case 502 -> {
                embed.addField(manager.getTranslation("error-api-embed-field-1-name"), "`502: Bad gateway`", false);
            }
            case 503 -> {
                embed.addField(manager.getTranslation("error-api-embed-field-1-name"), "`503: Service unavailable`", false);
            }
            case 504 -> {
                embed.addField(manager.getTranslation("error-api-embed-field-1-name"), "`504: Gateway timeout`", false);
            }
            case 429 -> {
                embed.setTitle(manager.getTranslation("embed-title-prefix") + manager.getTranslation("error-ratelimit-embed-title"));
                embed.setDescription(manager.getTranslation("error-ratelimit-embed-description"));
                embed.addField(manager.getTranslation("error-api-embed-field-1-name"), "`429: Too many requests`", false);
            }
        }
        return embed;
    }

}
