package dev.piste.vayna.interactions;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.interactions.commands.slash.ConnectionSlashCommand;
import dev.piste.vayna.interactions.util.exceptions.*;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emojis;
import dev.piste.vayna.util.Logger;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class ErrorHandler {

    private static final Logger LOGGER = new Logger(InteractionManager.class);

    public static void handleException(Exception exception, InteractionHook hook, Language language) {
        if(exception instanceof HttpErrorException httpErrorException) {
            handleHttpErrorException(httpErrorException, hook, language);
        } else if(exception instanceof InvalidRiotIdException invalidRiotIdException) {
            handleInvalidRiotIdException(invalidRiotIdException, hook, language);
        } else if(exception instanceof GuildConnectionsMissingException guildConnectionsMissingException) {
            handleGuildConnectionsMissingException(guildConnectionsMissingException, hook, language);
        } else if(exception instanceof InsufficientPermissionException insufficientPermissionException) {
            handleInsufficientPermissionException(insufficientPermissionException, hook, language);
        } else if(exception instanceof InvalidRegionException invalidRegionException) {
            handleInvalidRegionException(invalidRegionException, hook, language);
        } else if(exception instanceof RSOConnectionMissingException rsoConnectionMissingException) {
            handleRsoConnectionMissingException(rsoConnectionMissingException, hook, language);
        } else if(exception instanceof RSOConnectionPrivateException rsoConnectionPrivateException) {
            handleRsoConnectionPrivateException(rsoConnectionPrivateException, hook, language);
        } else {
            handleUnknownException(exception, hook, language);
        }
    }

    private static void handleHttpErrorException(HttpErrorException exception, InteractionHook hook, Language language) {
        LOGGER.error("HTTP Error", exception);

        Embed embed = new Embed()
                .setColor(255, 0, 0)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("error-api-embed-title"))
                .setDescription(language.getTranslation("error-api-embed-description"))
                .addField(language.getTranslation("error-api-embed-field-1-name"), "`" + exception.getStatusCode() + ": " + exception.getStatusReason() + "`", false);
        hook.editOriginalEmbeds(embed.build()).setActionRow(
                getSupportButton(language)
        ).queue();

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
    }

    private static void handleInvalidRiotIdException(InvalidRiotIdException exception, InteractionHook hook, Language language) {
        LOGGER.debug("Invalid Riot ID", exception);

        Embed embed = new Embed()
                .setColor(255, 0, 0)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("error-invalidriotid-embed-title"))
                .setDescription(language.getTranslation("error-invalidriotid-embed-desc")
                        .replaceAll("%emoji:riotgames%", Emojis.getRiotGames().getFormatted())
                        .replaceAll("%riotid%", exception.getRiotId()));
        hook.editOriginalEmbeds(embed.build()).setActionRow(
                getSupportButton(language)
        ).queue();
    }

    private static void handleGuildConnectionsMissingException(GuildConnectionsMissingException exception, InteractionHook hook, Language language) {
        LOGGER.debug("Guild Connections Missing", exception);

        Embed embed = new Embed()
                .setAuthor(exception.getGuild().getName(), exception.getGuild().getIconUrl())
                .setColor(255, 0, 0)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("error-guildconnectionsmissing-embed-title"))
                .setDescription(language.getTranslation("error-guildconnectionsmissing-embed-desc"));
        hook.editOriginalEmbeds(embed.build()).setActionRow(
                getSupportButton(language)
        ).queue();
    }

    private static void handleInsufficientPermissionException(InsufficientPermissionException exception, InteractionHook hook, Language language) {
        LOGGER.debug("Insufficient Permission", exception);

        Embed embed = new Embed()
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("error-permission-embed-title"))
                .setDescription(language.getTranslation("error-permission-embed-desc")
                        .replaceAll("%permission%", exception.getPermission().getName()))
                .setColor(255, 0, 0);
        hook.editOriginalEmbeds(embed.build()).setActionRow(
                getSupportButton(language)
        ).queue();
    }

    private static void handleInvalidRegionException(InvalidRegionException exception, InteractionHook hook, Language language) {
        LOGGER.debug("Invalid Region", exception);

        Embed embed = new Embed()
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("error-invalidregion-embed-title"))
                .setDescription(language.getTranslation("error-invalidregion-embed-desc")
                        .replaceAll("%emoji:riotgames%", Emojis.getRiotGames().getFormatted())
                        .replaceAll("%riotid%", exception.getRiotId()))
                .setColor(255, 0, 0);
        hook.editOriginalEmbeds(embed.build()).setActionRow(
                getSupportButton(language)
        ).queue();
    }

    private static void handleRsoConnectionMissingException(RSOConnectionMissingException exception, InteractionHook hook, Language language) {
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

        hook.editOriginalEmbeds(embed.build()).setActionRow(
                getSupportButton(language)
        ).queue();
    }

    private static void handleRsoConnectionPrivateException(RSOConnectionPrivateException exception, InteractionHook hook, Language language) {
        LOGGER.debug("RSO Connection Private", exception);

        Embed embed = new Embed()
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("error-rsoconnectionprivate-embed-title"))
                .setDescription(language.getTranslation("error-rsoconnectionprivate-embed-desc")
                        .replaceAll("%user:mention%", exception.getTarget().getAsMention()))
                .setColor(255, 0, 0);

        hook.editOriginalEmbeds(embed.build()).setActionRow(
                getSupportButton(language)
        ).queue();
    }

    private static void handleUnknownException(Exception exception, InteractionHook hook, Language language) {
        LOGGER.error("Unknown Error", exception);

        Embed embed = new Embed()
                .setColor(255, 0, 0)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("error-unknown-embed-title"))
                .setDescription(language.getTranslation("error-unknown-embed-desc"));

        hook.editOriginalEmbeds(embed.build()).setActionRow(
                getSupportButton(language)
        ).queue();
    }

    private static Button getSupportButton(Language language) {
        return Button.link(ConfigManager.getSettingsConfig().getSupportGuildInviteUri(), language.getTranslation("button-support-name")).withEmoji(Emojis.getDiscord());
    }

}