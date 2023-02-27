package dev.piste.vayna.listener;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.interactions.InteractionManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Logger;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class InteractionListeners extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        performInteraction(LanguageManager.getLanguage(event.getGuild()), event, event.getHook());
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        performInteraction(LanguageManager.getLanguage(event.getGuild()), event, event.getHook());
    }

    @Override
    public void onUserContextInteraction(@NotNull UserContextInteractionEvent event) {
        performInteraction(LanguageManager.getLanguage(event.getGuild()), event, event.getHook());
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        performInteraction(LanguageManager.getLanguage(event.getGuild()), event, event.getHook());
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        performInteraction(LanguageManager.getLanguage(event.getGuild()), event, event.getHook());
    }

    private void performInteraction(Language language, GenericEvent event, InteractionHook interactionHook) {
        Thread thread = new Thread(() -> {
            try {
                InteractionManager.perform(event);
            } catch (Exception e) {
                handleException(language, interactionHook, e);
            }
        });
        thread.setName("InteractionEvent");
        thread.start();
    }

    private void handleException(Language language, InteractionHook interactionHook, Throwable throwable) {
        if(throwable instanceof HttpErrorException) {
            new Logger(InteractionListeners.class).error("HTTP Error", throwable);
            String statusCodeText = switch (((HttpErrorException) throwable).getStatusCode()) {
                case 400 -> "Bad request";
                case 401 -> "Unauthorized";
                case 403 -> "Forbidden";
                case 404 -> "Not found";
                case 405 -> "Method not allowed";
                case 408 -> "Timeout";
                case 500 -> "Internal server error";
                case 502 -> "Bad gateway";
                case 503 -> "Service unavailable";
                case 504 -> "Gateway timeout";
                case 429 -> "Too many requests";
                default -> "Unknown";
            };
            Embed embed = new Embed()
                    .setColor(255, 0, 0)
                    .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("error-api-embed-title"))
                    .setDescription(language.getTranslation("error-api-embed-description"))
                    .addField(language.getTranslation("error-api-embed-field-1-name"), "`" + ((HttpErrorException) throwable).getStatusCode() + ": " + statusCodeText + "`", false);
            interactionHook.editOriginalEmbeds(embed.build()).setActionRow(
                    Buttons.getSupportButton(language)
            ).queue();

            // Error logging
            Embed logEmbed = new Embed()
                    .setColor(255, 0, 0)
                    .addField("URL", ((HttpErrorException) throwable).getUri(), true)
                    .addField("Request Method", ((HttpErrorException) throwable).getRequestMethod(), true)
                    .addField("Response Body", "```json\n" + ((HttpErrorException) throwable).getResponseBody() + "```", false);
            TextChannel logChannel = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuildId()).getTextChannelById(ConfigManager.getSettingsConfig().getLogChannelIds().getError());
            logChannel.sendMessageEmbeds(logEmbed.build()).queue();
        } else {
            new Logger(InteractionListeners.class).error("Unknown Error", throwable);
            Embed embed = new Embed()
                    .setColor(255, 0, 0)
                    .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("error-unknown-embed-title"))
                    .setDescription(language.getTranslation("error-unknown-embed-description"));
            interactionHook.editOriginalEmbeds(embed.build()).setActionRow(
                    Buttons.getSupportButton(language)
            ).queue();

            // Error logging
            Embed logEmbed = new Embed()
                    .setColor(255, 0, 0)
                    .setTitle("Unknown Error")
                    .addField("Exception", throwable.getClass().getName(), true);
            if(throwable.getMessage() != null) {
                logEmbed.addField("Message", throwable.getMessage(), true);
            }
            if(throwable.getCause() != null) {
                logEmbed.addField("Cause", throwable.getCause().getClass().getName(), true);
            }
            logEmbed.addField("Stacktrace", "```" + Arrays.toString(throwable.getStackTrace()) + "```", false);
            TextChannel logChannel = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuildId()).getTextChannelById(ConfigManager.getSettingsConfig().getLogChannelIds().getError());
            logChannel.sendMessageEmbeds(logEmbed.build()).queue();
        }
    }

}
