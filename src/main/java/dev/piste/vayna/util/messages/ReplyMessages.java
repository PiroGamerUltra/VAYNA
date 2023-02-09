package dev.piste.vayna.util.messages;

import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import dev.piste.vayna.util.Language;
import dev.piste.vayna.util.LanguageManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

/**
 * @author Piste | https://github.com/zPiste
 */
public class ReplyMessages {

    public static MessageEmbed getConnectionNone(Guild guild, User user) {
        Language language = LanguageManager.getLanguage(guild);
        Embed embed = new Embed();
        embed.setColor(209, 54, 57);
        embed.setAuthor(user.getName(), ConfigManager.getSettingsConfig().getWebsiteUri(), user.getAvatarUrl());
        embed.setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-connection-none-embed-title"));
        embed.setDescription(language.getTranslation("command-connection-none-embed-description"));
        return embed.build();
    }

    public static MessageEmbed getConnectionPresent(Guild guild, User user, String riotId, boolean visibleToPublic) {
        Language language = LanguageManager.getLanguage(guild);
        Embed embed = new Embed();
        embed.setAuthor(user.getName(), ConfigManager.getSettingsConfig().getWebsiteUri(), user.getAvatarUrl());
        embed.setColor(209, 54, 57);
        embed.setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-connection-present-embed-title"));
        embed.setDescription(language.getTranslation("command-connection-present-embed-description"));
        embed.addField(language.getTranslation("command-connection-present-embed-field-1-name"),
                user.getAsMention() + " " + Emoji.getDiscord().getFormatted() + " \uD83D\uDD17 " + Emoji.getRiotGames().getFormatted() + " `" + riotId + "`", true);
        embed.addField(language.getTranslation("command-connection-present-embed-field-2-name"),
                visibleToPublic ? "\uD83D\uDD13 " + language.getTranslation("command-connection-present-embed-field-2-text-public")
                        : "\uD83D\uDD12 " + language.getTranslation("command-connection-present-embed-field-2-text-private"), true);
        return embed.build();
    }

    public static MessageEmbed getSettings(Guild guild) {
        Language language = LanguageManager.getLanguage(guild);

        return new Embed()
                .setAuthor(guild.getName(), ConfigManager.getSettingsConfig().getWebsiteUri(), guild.getIconUrl())
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-settings-embed-title"))
                .addField(language.getTranslation("command-settings-embed-field-1-name"),
                        language.getTranslation("language-emoji") + " " + language.getTranslation("language-name"), false)
                .build();
    }

}
