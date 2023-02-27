package dev.piste.vayna.util.templates;

import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.interactions.InteractionManager;
import dev.piste.vayna.interactions.commands.slash.ConnectionSlashCommand;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emojis;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class ErrorMessages {

    public static MessageEmbed getInvalidRegion(Guild guild, User user, RiotAccount riotAccount) {
        Language language = LanguageManager.getLanguage(guild);

        Embed embed = new Embed()
                .setAuthor(user.getName(), user.getAvatarUrl())
                .setColor(255, 0, 0)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-stats-error-region-embed-title"))
                .setDescription(language.getTranslation("command-stats-error-region-embed-description")
                        .replaceAll("%emoji:riotgames%", Emojis.getRiotGames().getFormatted())
                        .replaceAll("%riotid%", riotAccount.getRiotId()));
        return embed.build();
    }

    public static MessageEmbed getPrivate(Guild guild, User user) {
        Language language = LanguageManager.getLanguage(guild);
        Embed embed = new Embed().setAuthor(user.getName(), user.getAvatarUrl())
                .setColor(255, 0, 0)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-stats-error-private-embed-title"))
                .setDescription(language.getTranslation("command-stats-error-private-embed-description"));
        return embed.build();
    }

    public static MessageEmbed getNoConnection(Guild guild, User user, String targetMention) {
        Language language = LanguageManager.getLanguage(guild);
        return new Embed().setAuthor(user.getName(), user.getAvatarUrl())
                .setColor(255, 0, 0)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-stats-error-noconnection-embed-title"))
                .setDescription(language.getTranslation("command-stats-error-noconnection-embed-description")
                        .replaceAll("%user:target%", targetMention))
                .build();
    }

    public static MessageEmbed getNoConnectionSelf(Guild guild, User user) {
        Language language = LanguageManager.getLanguage(guild);
        return new Embed().setAuthor(user.getName(), user.getAvatarUrl())
                .setColor(255, 0, 0)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-stats-error-noconnectionself-embed-title"))
                .setDescription(language.getTranslation("command-stats-error-noconnectionself-embed-description")
                        .replaceAll("%command:connection%", InteractionManager.getSlashCommandAsJdaCommand(new ConnectionSlashCommand()).getAsMention()))
                .build();
    }

}
