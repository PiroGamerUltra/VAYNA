package dev.piste.vayna.listener;

import dev.piste.vayna.Bot;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.config.settings.SettingsConfig;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author Piste | https://github.com/zPiste
 */
public class ModalInteractionListener extends ListenerAdapter {

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if(event.getModalId().equalsIgnoreCase("feedback")) {
            if(Bot.isDebug()) return;
            event.deferReply().setEphemeral(true).queue();
            SettingsConfig settingsConfig = Configs.getSettings();
            TextChannel feedbackChannel = Bot.getJDA().getGuildById(settingsConfig.getSupportGuild().getId()).getTextChannelById(settingsConfig.getLogChannels().getFeedback());
            Embed feedbackEmbed = new Embed().setAuthor(event.getUser().getAsTag(), settingsConfig.getWebsiteUri(), event.getUser().getAvatarUrl())
                    .setTitle("» Feedback")
                    .setThumbnail(event.getUser().getAvatarUrl())
                    .setDescription(event.getValues().get(0).getAsString());
            feedbackChannel.sendMessageEmbeds(feedbackEmbed.build()).queue();
            Embed replyEmbed = new Embed().setAuthor(event.getUser().getName(), settingsConfig.getWebsiteUri(), event.getUser().getAvatarUrl())
                    .setTitle("» Success")
                    .setColor(0, 255, 0)
                    .setDescription("Your feedback has successfully been shared with the developer of **VAYNA**. Thank you!");
            event.getHook().editOriginalEmbeds(replyEmbed.build()).queue();
        }
    }

}
