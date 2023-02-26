package dev.piste.vayna.interactions.managers;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.interactions.buttons.DisconnectButton;
import dev.piste.vayna.interactions.buttons.HistoryButton;
import dev.piste.vayna.interactions.buttons.VisibilityButton;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.ErrorMessages;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.util.HashMap;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class ButtonManager {

    private static final HashMap<String, Button> buttons = new HashMap<>();

    public static void registerButtons() {
        addButton(new DisconnectButton());
        addButton(new VisibilityButton());
        addButton(new HistoryButton());
    }

    private static void addButton(Button button) {
        buttons.put(button.getName(), button);
    }

    public static void perform(ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId().split(";")[0];
        String[] args = event.getButton().getId().substring(buttonId.length()).split(";");
        Thread thread = new Thread(() -> {
            try {
                buttons.get(buttonId).perform(event, args);
            } catch (HttpErrorException e) {
                Embed embed = ErrorMessages.getStatusCodeErrorEmbed(event.getGuild(), event.getUser(), e);

                event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                        Buttons.getSupportButton(event.getGuild())
                ).queue();
                if(Bot.isDebug()) return;
                TextChannel logChannel = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuildId()).getTextChannelById(ConfigManager.getSettingsConfig().getLogChannelIds().getError());
                embed.addField("URL", e.getUri(), false)
                        .addField("Request Method", e.getRequestMethod(), false)
                        .setAuthor(event.getUser().getAsTag(), event.getUser().getAvatarUrl())
                        .setDescription(" ");
                logChannel.sendMessageEmbeds(embed.build()).queue();
            }
        });
        thread.start();
    }

}
