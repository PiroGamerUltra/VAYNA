package dev.piste.vayna.modals;

import dev.piste.vayna.Bot;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.config.settings.SettingsConfig;
import dev.piste.vayna.config.translations.Language;
import dev.piste.vayna.manager.Modal;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

/**
 * @author Piste | https://github.com/zPiste
 */
public class FeedbackModal implements Modal {


    @Override
    public void perform(ModalInteractionEvent event) {
        if(Bot.isDebug()) return;
        Language language = Language.getLanguage(event.getGuild());

        event.deferReply().setEphemeral(true).queue();
        SettingsConfig settingsConfig = Configs.getSettings();
        TextChannel feedbackChannel = Bot.getJDA().getGuildById(settingsConfig.getSupportGuild().getId()).getTextChannelById(settingsConfig.getLogChannels().getFeedback());
        Embed feedbackEmbed = new Embed().setAuthor(event.getUser().getAsTag(), settingsConfig.getWebsiteUri(), event.getUser().getAvatarUrl())
                .setTitle("» Feedback")
                .setThumbnail(event.getUser().getAvatarUrl())
                .setDescription(event.getValues().get(0).getAsString());
        feedbackChannel.sendMessageEmbeds(feedbackEmbed.build()).queue();
        event.getHook().editOriginalEmbeds(language.getModals().getFeedback().getMessageEmbed(event.getUser())).queue();
    }

    @Override
    public String getName() {
        return "feedback";
    }
}
