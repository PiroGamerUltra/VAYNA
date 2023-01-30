package dev.piste.vayna.util.messages;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.config.translations.Errors;
import dev.piste.vayna.config.translations.Language;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

/**
 * @author Piste | https://github.com/zPiste
 */
public class ErrorMessages {

    public static Embed getStatusCodeErrorMessage(User user, Guild guild, StatusCodeException exception) {
        String[] message = exception.getMessage().split(" ");
        int statusCode = Integer.parseInt(message[0]);

        Language language = Language.getLanguage(guild);

        Errors errorTranslations = language.getError();
        Embed embed = new Embed()
                .setColor(255, 0, 0)
                .setTitle(language.getPrefix() + errorTranslations.getApi().getTitle())
                .setDescription(errorTranslations.getApi().getDescription())
                .setAuthor(user.getName(), Configs.getSettings().getWebsiteUri(), user.getAvatarUrl());
        switch (statusCode) {
            case 400 -> {
                embed.addField(errorTranslations.getApi().getFieldTitle(), "`400: Bad request`", false);
            }
            case 401 -> {
                embed.addField(errorTranslations.getApi().getFieldTitle(), "`401: Unauthorized`", false);
            }
            case 403 -> {
                embed.addField(errorTranslations.getApi().getFieldTitle(), "`403: Forbidden`", false);
            }
            case 404 -> {
                embed.addField(errorTranslations.getApi().getFieldTitle(), "`404: Not found`", false);
            }
            case 405 -> {
                embed.addField(errorTranslations.getApi().getFieldTitle(), "`405: Method not allowed`", false);
            }
            case 408 -> {
                embed.addField(errorTranslations.getApi().getFieldTitle(), "`408: Timeout`", false);
            }
            case 500 -> {
                embed.addField(errorTranslations.getApi().getFieldTitle(), "`500: Internal server error`", false);
            }
            case 502 -> {
                embed.addField(errorTranslations.getApi().getFieldTitle(), "`502: Bad gateway`", false);
            }
            case 503 -> {
                embed.addField(errorTranslations.getApi().getFieldTitle(), "`503: Service unavailable`", false);
            }
            case 504 -> {
                embed.addField(errorTranslations.getApi().getFieldTitle(), "`504: Gateway timeout`", false);
            }
            case 429 -> {
                embed.setTitle(language.getPrefix() + errorTranslations.getRateLimit().getTitle())
                        .setDescription(errorTranslations.getRateLimit().getDescription());
            }
        }
        return embed;
    }

    public static MessageEmbed getInvalidRiotIdMessage(User user, Guild guild, String riotId) {
        Language language = Language.getLanguage(guild);

        Embed embed = new Embed().setAuthor(user.getName(), Configs.getSettings().getWebsiteUri(), user.getAvatarUrl())
                .setColor(255, 0, 0)
                .setTitle(language.getPrefix() + language.getError().getRiotId().getTitle())
                .setDescription(language.getError().getRiotId().getDescription()
                        .replaceAll("%emoji:riotgames%", Emoji.getRiotGames().getFormatted())
                        .replaceAll("%riotid%", riotId));
        return embed.build();
    }

}
