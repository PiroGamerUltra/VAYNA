package dev.piste.vayna.listener;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.interactions.InteractionManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Logger;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.translations.LanguageManager;
import net.dv8tion.jda.api.entities.User;
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

    private static final Logger LOGGER = new Logger(InteractionListeners.class);

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
                handleException(language, interactionHook, interactionHook.getInteraction().getUser(), e);
            }
        });
        thread.setName("InteractionEvent");
        thread.start();
    }

    private void handleException(Language language, InteractionHook interactionHook, User user, Throwable throwable) {
        if(throwable instanceof HttpErrorException httpErrorException) {
            LOGGER.error("HTTP Error", throwable);
            Embed embed = new Embed()
                    .setColor(255, 0, 0)
                    .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("error-api-embed-title"))
                    .setDescription(language.getTranslation("error-api-embed-description"))
                    .addField(language.getTranslation("error-api-embed-field-1-name"), "`" + httpErrorException.getStatusCode() + ": " + httpErrorException.getStatusReason() + "`", false);
            interactionHook.editOriginalEmbeds(embed.build()).setActionRow(
                    Buttons.getSupportButton(language)
            ).queue();

            // Error logging
            Embed logEmbed = new Embed()
                    .setColor(255, 0, 0)
                    .setTitle("» HTTP Error")
                    .setAuthor(user.getAsTag(), user.getAvatarUrl())
                    .addField("Request Method", "`" + httpErrorException.getRequestMethod() + "`", true)
                    .addField("Request URI", httpErrorException.getUri(), true)
                    .addField("Response Code", "`" + httpErrorException.getStatusCode() + " (" + httpErrorException.getStatusReason() + ")`", true)
                    .addField("Response Body", "```json\n" + httpErrorException.getResponseBody() + "```", false);
            TextChannel logChannel = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuildId()).getTextChannelById(ConfigManager.getSettingsConfig().getLogChannelIds().getError());
            logChannel.sendMessageEmbeds(logEmbed.build()).queue();
        } else {
            LOGGER.error("Unknown Error", throwable);
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
                    .setAuthor(user.getAsTag(), user.getAvatarUrl())
                    .setTitle("» Unknown Error")
                    .addField("Exception", "`" + throwable.getClass().getName() + "`", true);
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
