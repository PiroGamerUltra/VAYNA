package dev.piste.vayna.util.messages;

import dev.piste.vayna.config.Configs;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import dev.piste.vayna.util.TranslationManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

/**
 * @author Piste | https://github.com/zPiste
 */
public class ReplyMessages {

    public static MessageEmbed getConnectionNone(Guild guild, User user) {
        TranslationManager manager = TranslationManager.getTranslation(guild);
        Embed embed = new Embed();
        embed.setColor(209, 54, 57);
        embed.setAuthor(user.getName(), Configs.getSettings().getWebsiteUri(), user.getAvatarUrl());
        embed.setTitle(manager.getTranslation("embed-title-prefix") + manager.getTranslation("command-connection-none-embed-title"));
        embed.setDescription(manager.getTranslation("command-connection-none-embed-description"));
        return embed.build();
    }

    public static MessageEmbed getConnectionPresent(Guild guild, User user, String riotId, boolean visibleToPublic) {
        TranslationManager manager = TranslationManager.getTranslation(guild);
        Embed embed = new Embed();
        embed.setAuthor(user.getName(), Configs.getSettings().getWebsiteUri(), user.getAvatarUrl());
        embed.setColor(209, 54, 57);
        embed.setTitle(manager.getTranslation("embed-title-prefix") + manager.getTranslation("command-connection-present-embed-title"));
        embed.setDescription(manager.getTranslation("command-connection-present-embed-description"));
        embed.addField(manager.getTranslation("command-connection-present-embed-field-1-name"),
                user.getAsMention() + " " + Emoji.getDiscord().getFormatted() + " \uD83D\uDD17 " + Emoji.getRiotGames().getFormatted() + " `" + riotId + "`", true);
        embed.addField(manager.getTranslation("command-connection-present-embed-field-2-name"),
                visibleToPublic ? "\uD83D\uDD13 " + manager.getTranslation("command-connection-present-embed-field-2-text-public")
                        : "\uD83D\uDD12 " + manager.getTranslation("command-connection-present-embed-field-2-text-private"), true);
        return embed.build();
    }

    public static MessageEmbed getSettings(Guild guild) {
        TranslationManager translation = TranslationManager.getTranslation(guild);

        return new Embed()
                .setAuthor(guild.getName(), Configs.getSettings().getWebsiteUri(), guild.getIconUrl())
                .setTitle(translation.getTranslation("embed-title-prefix") + translation.getTranslation("command-settings-embed-title"))
                .addField(translation.getTranslation("command-settings-embed-field-1-name"),
                        translation.getTranslation("language-emoji") + " " + translation.getTranslation("language-name"), false)
                .build();
    }

}
