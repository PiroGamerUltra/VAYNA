package dev.piste.vayna.util.templates;

import dev.piste.vayna.apis.entities.riotgames.RiotAccount;
import dev.piste.vayna.interactions.InteractionManager;
import dev.piste.vayna.interactions.commands.slash.ConnectionSlashCommand;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emojis;
import net.dv8tion.jda.api.entities.MessageEmbed;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class ErrorMessages {

    public static MessageEmbed getInvalidRegion(RiotAccount riotAccount, Language language) {
        return new Embed()
                .setColor(255, 0, 0)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-stats-error-region-embed-title"))
                .setDescription(language.getTranslation("command-stats-error-region-embed-description")
                        .replaceAll("%emoji:riotgames%", Emojis.getRiotGames().getFormatted())
                        .replaceAll("%riotid%", riotAccount.getRiotId()))
                .build();
    }

    public static MessageEmbed getPrivate(Language language) {
        return new Embed()
                .setColor(255, 0, 0)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-stats-error-private-embed-title"))
                .setDescription(language.getTranslation("command-stats-error-private-embed-description"))
                .build();
    }

    public static MessageEmbed getNoConnection(String targetMention, Language language) {
        return new Embed()
                .setColor(255, 0, 0)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-stats-error-noconnection-embed-title"))
                .setDescription(language.getTranslation("command-stats-error-noconnection-embed-description")
                        .replaceAll("%user:target%", targetMention))
                .build();
    }

    public static MessageEmbed getNoConnectionSelf(Language language) {
        return new Embed()
                .setColor(255, 0, 0)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-stats-error-noconnectionself-embed-title"))
                .setDescription(language.getTranslation("command-stats-error-noconnectionself-embed-description")
                        .replaceAll("%command:connection%", InteractionManager.getSlashCommandAsJdaCommand(new ConnectionSlashCommand()).getAsMention()))
                .build();
    }

}
