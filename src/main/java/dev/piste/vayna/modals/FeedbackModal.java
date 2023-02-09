package dev.piste.vayna.modals;

import dev.piste.vayna.Bot;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.config.settings.SettingsConfig;
import dev.piste.vayna.manager.Modal;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Language;
import dev.piste.vayna.util.LanguageManager;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

/**
 * @author Piste | https://github.com/zPiste
 */
public class FeedbackModal implements Modal {


    @Override
    public void perform(ModalInteractionEvent event) {
        event.deferReply().setEphemeral(true).queue();

        Language language = LanguageManager.getLanguage(event.getGuild());

        Embed embed = new Embed().setAuthor(event.getUser().getName(), Configs.getSettings().getWebsiteUri(), event.getUser().getAvatarUrl())
                .setColor(0, 255, 0)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("modal-feedback-embed-title"))
                .setDescription(language.getTranslation("modal-feedback-embed-description"));
        event.getHook().editOriginalEmbeds(embed.build()).queue();

        if(Bot.isDebug()) return;
        SettingsConfig settingsConfig = Configs.getSettings();
        TextChannel feedbackChannel = Bot.getJDA().getGuildById(settingsConfig.getSupportGuild().getId()).getTextChannelById(settingsConfig.getLogChannels().getFeedback());
        Embed feedbackEmbed = new Embed().setAuthor(event.getUser().getAsTag(), settingsConfig.getWebsiteUri(), event.getUser().getAvatarUrl())
                .setTitle("» Feedback")
                .setThumbnail(event.getUser().getAvatarUrl())
                .setDescription(event.getValues().get(0).getAsString());
        feedbackChannel.sendMessageEmbeds(feedbackEmbed.build()).queue();
    }

    @Override
    public String getName() {
        return "feedback";
    }
}
