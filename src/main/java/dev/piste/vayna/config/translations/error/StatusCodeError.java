package dev.piste.vayna.config.translations.error;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.config.translations.Errors;
import dev.piste.vayna.config.translations.Language;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

/**
 * @author Piste | https://github.com/zPiste
 */
public class StatusCodeError {

    private String title;
    private String description;
    private String fieldTitle;

    public Embed getEmbed(User user, StatusCodeException exception) {
        String[] message = exception.getMessage().split(" ");
        int statusCode = Integer.parseInt(message[0]);

        Embed embed = new Embed()
                .setColor(255, 0, 0)
                .setTitle(Configs.getTranslations().getTitlePrefix() + title)
                .setDescription(description)
                .setAuthor(user.getName(), Configs.getSettings().getWebsiteUri(), user.getAvatarUrl());
        switch (statusCode) {
            case 400 -> {
                embed.addField(fieldTitle, "`400: Bad request`", false);
            }
            case 401 -> {
                embed.addField(fieldTitle, "`401: Unauthorized`", false);
            }
            case 403 -> {
                embed.addField(fieldTitle, "`403: Forbidden`", false);
            }
            case 404 -> {
                embed.addField(fieldTitle, "`404: Not found`", false);
            }
            case 405 -> {
                embed.addField(fieldTitle, "`405: Method not allowed`", false);
            }
            case 408 -> {
                embed.addField(fieldTitle, "`408: Timeout`", false);
            }
            case 500 -> {
                embed.addField(fieldTitle, "`500: Internal server error`", false);
            }
            case 502 -> {
                embed.addField(fieldTitle, "`502: Bad gateway`", false);
            }
            case 503 -> {
                embed.addField(fieldTitle, "`503: Service unavailable`", false);
            }
            case 504 -> {
                embed.addField(fieldTitle, "`504: Gateway timeout`", false);
            }
            case 429 -> {
                embed.addField(fieldTitle, "`429: Too many requests`", false);
            }
        }
        return embed;
    }


}
