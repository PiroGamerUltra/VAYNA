package dev.piste.vayna.interactions.modals;

import dev.piste.vayna.Bot;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.translations.Language;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class FeedbackModal implements IModal {

    @Override
    public void perform(ModalInteractionEvent event, Language language) {
        event.deferReply(true).queue();

        Embed embed = new Embed()
                .setAuthor(event.getUser().getName(), event.getUser().getAvatarUrl())
                .setColor(0, 255, 0)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("modal-feedback-embed-title"))
                .setDescription(language.getTranslation("modal-feedback-embed-description"));
        event.getHook().editOriginalEmbeds(embed.build()).queue();

        if(Bot.isDebug()) return;
        TextChannel feedbackChannel = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuildId()).getTextChannelById(ConfigManager.getSettingsConfig().getLogChannelIds().getFeedback());
        Embed feedbackEmbed = new Embed()
                .setAuthor(event.getUser().getAsTag(), event.getUser().getAvatarUrl())
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
