package dev.piste.vayna.interactions;

import dev.piste.vayna.Bot;
import dev.piste.vayna.http.HttpErrorException;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.interactions.commands.slash.ConnectionSlashCommand;
import dev.piste.vayna.interactions.util.exceptions.*;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.util.DiscordEmoji;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Logger;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class ErrorHandler {

    private static final Logger LOGGER = new Logger(InteractionManager.class);

    private ErrorHandler() {
        throw new IllegalStateException("Utility class");
    }

    public static void handleException(Exception exception, InteractionHook hook, Language language) {
        MessageEmbed messageEmbed;
        if(exception instanceof HttpErrorException httpErrorException) {
            messageEmbed = getHttpErrorExceptionEmbed(httpErrorException, language);
        } else if(exception instanceof InvalidRiotIdException invalidRiotIdException) {
            messageEmbed = getInvalidRiotIdExceptionEmbed(invalidRiotIdException, language);
        } else if(exception instanceof GuildConnectionsMissingException guildConnectionsMissingException) {
            messageEmbed = getGuildConnectionsMissingExceptionEmbed(guildConnectionsMissingException, language);
        } else if(exception instanceof InsufficientPermissionException insufficientPermissionException) {
            messageEmbed = getInsufficientPermissionExceptionEmbed(insufficientPermissionException, language);
        } else if(exception instanceof InvalidRegionException invalidRegionException) {
            messageEmbed = getInvalidRegionExceptionEmbed(invalidRegionException, language);
        } else if(exception instanceof RSOConnectionMissingException rsoConnectionMissingException) {
            messageEmbed = getRsoConnectionMissingExceptionEmbed(rsoConnectionMissingException, hook, language);
        } else if(exception instanceof RSOConnectionPrivateException rsoConnectionPrivateException) {
            messageEmbed = getRsoConnectionPrivateExceptionEmbed(rsoConnectionPrivateException, language);
        } else {
            messageEmbed = getUnknownExceptionEmbed(exception, language);
        }
        hook.editOriginalEmbeds(messageEmbed).setActionRow(
                Button.link(ConfigManager.getSettingsConfig().getSupportGuildInviteUri(), language.getTranslation("button-support-name")).withEmoji(DiscordEmoji.DISCORD.getAsDiscordEmoji())
        ).queue();
    }

    private static MessageEmbed getHttpErrorExceptionEmbed(HttpErrorException exception, Language language) {
        LOGGER.error("HTTP Error", exception);

        Embed embed = new Embed()
                .setColor(255, 0, 0)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("error-api-embed-title"))
                .setDescription(language.getTranslation("error-api-embed-description"))
                .addField(language.getTranslation("error-api-embed-field-1-name"), "`" + exception.getStatusCode() + ": " + exception.getStatusReason() + "`", false);

        // Log
        Embed logEmbed = new Embed()
                .setColor(255, 0, 0)
                .setTitle("» HTTP Error")
                .addField("Request Method", "`" + exception.getRequestMethod() + "`", true)
                .addField("Request URI", exception.getURI(), true)
                .addField("Response Code", "`" + exception.getStatusCode() + " (" + exception.getStatusReason() + ")`", true)
                .addField("Response Body", "```json\n" + exception.getResponseBody() + "```", false);
        TextChannel logChannel = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuildId()).getTextChannelById(ConfigManager.getSettingsConfig().getLogChannelIds().getError());
        logChannel.sendMessageEmbeds(logEmbed.build()).queue();

        return embed.build();
    }

    private static MessageEmbed getInvalidRiotIdExceptionEmbed(InvalidRiotIdException exception, Language language) {
        LOGGER.debug("Invalid Riot ID", exception);

        Embed embed = new Embed()
                .setColor(255, 0, 0)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("error-invalidriotid-embed-title"))
                .setDescription(language.getTranslation("error-invalidriotid-embed-desc")
                        .replaceAll("%emoji:riotgames%", DiscordEmoji.RIOT_GAMES.getAsDiscordEmoji().getFormatted())
                        .replaceAll("%riotid%", exception.getRiotId()));

        return embed.build();
    }

    private static MessageEmbed getGuildConnectionsMissingExceptionEmbed(GuildConnectionsMissingException exception, Language language) {
        LOGGER.debug("Guild Connections Missing", exception);

        Embed embed = new Embed()
                .setAuthor(exception.getGuild().getName(), exception.getGuild().getIconUrl())
                .setColor(255, 0, 0)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("error-guildconnectionsmissing-embed-title"))
                .setDescription(language.getTranslation("error-guildconnectionsmissing-embed-desc"));

        return embed.build();
    }

    private static MessageEmbed getInsufficientPermissionExceptionEmbed(InsufficientPermissionException exception, Language language) {
        LOGGER.debug("Insufficient Permission", exception);

        Embed embed = new Embed()
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("error-permission-embed-title"))
                .setDescription(language.getTranslation("error-permission-embed-desc")
                        .replaceAll("%permission%", exception.getPermission().getName()))
                .setColor(255, 0, 0);

        return embed.build();
    }

    private static MessageEmbed getInvalidRegionExceptionEmbed(InvalidRegionException exception, Language language) {
        LOGGER.debug("Invalid Region", exception);

        Embed embed = new Embed()
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("error-invalidregion-embed-title"))
                .setDescription(language.getTranslation("error-invalidregion-embed-desc")
                        .replaceAll("%emoji:riotgames%", DiscordEmoji.RIOT_GAMES.getAsDiscordEmoji().getFormatted())
                        .replaceAll("%riotid%", exception.getRiotId()))
                .setColor(255, 0, 0);

        return embed.build();
    }

    private static MessageEmbed getRsoConnectionMissingExceptionEmbed(RSOConnectionMissingException exception, InteractionHook hook, Language language) {
        LOGGER.debug("RSO Connection Missing", exception);

        Embed embed = new Embed()
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("error-rsoconnectionmissing-embed-title"))
                .setColor(255, 0, 0);

        if(exception.getTarget().getIdLong() == hook.getInteraction().getUser().getIdLong()) {
            embed.setDescription(language.getTranslation("error-rsoconnectionmissing-embed-desc-self")
                    .replaceAll("%command:connection%", InteractionManager.getSlashCommandAsJdaCommand(new ConnectionSlashCommand()).getAsMention()));
        } else {
            embed.setDescription(language.getTranslation("error-rsoconnectionmissing-embed-desc-other")
                    .replaceAll("%user:mention%", exception.getTarget().getAsMention()));
        }

        return embed.build();
    }

    private static MessageEmbed getRsoConnectionPrivateExceptionEmbed(RSOConnectionPrivateException exception, Language language) {
        LOGGER.debug("RSO Connection Private", exception);

        Embed embed = new Embed()
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("error-rsoconnectionprivate-embed-title"))
                .setDescription(language.getTranslation("error-rsoconnectionprivate-embed-desc")
                        .replaceAll("%user:mention%", exception.getTarget().getAsMention()))
                .setColor(255, 0, 0);

        return embed.build();
    }

    private static MessageEmbed getUnknownExceptionEmbed(Exception exception, Language language) {
        LOGGER.error("Unknown Error", exception);

        Embed embed = new Embed()
                .setColor(255, 0, 0)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("error-unknown-embed-title"))
                .setDescription(language.getTranslation("error-unknown-embed-desc"));

        return embed.build();
    }

}