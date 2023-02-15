package dev.piste.vayna.manager;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.commands.button.DisconnectButton;
import dev.piste.vayna.commands.button.HistoryButton;
import dev.piste.vayna.commands.button.VisibilityButton;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.ErrorMessages;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.util.HashMap;

/**
 * @author Piste | https://github.com/zPiste
 */
public class ButtonManager {

    private static final HashMap<String, Button> buttons = new HashMap<>();

    private static final HashMap<String, RiotAccount> statsButtonMap = new HashMap<>();

    public static void registerButtons() {
        addButton(new DisconnectButton());
        addButton(new VisibilityButton());
        addButton(new HistoryButton());
    }

    private static void addButton(Button button) {
        buttons.put(button.getName().substring(0, button.getName().length()-1), button);
    }

    public static void putInStatsButtonMap(String uuid, RiotAccount riotAccount) {
        statsButtonMap.put(uuid, riotAccount);
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(600000);
                statsButtonMap.remove(uuid);
            } catch (InterruptedException e) {
                statsButtonMap.remove(uuid);
            }
        });
        thread.start();
    }

    public static RiotAccount getRiotAccountFromStatsButtonMap(String uuid) {
        if(statsButtonMap.get(uuid) == null) return null;
        return statsButtonMap.get(uuid);
    }

    public static void perform(ButtonInteractionEvent event) {
        String[] buttonId = event.getButton().getId().split(";");
        Thread thread = new Thread(() -> {
            try {
                buttons.get(buttonId[0]).perform(event, (buttonId.length == 1 ? null : buttonId[1]));
            } catch (StatusCodeException e) {
                Embed embed = ErrorMessages.getStatusCodeErrorEmbed(event.getGuild(), event.getUser(), e);

                event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                        Buttons.getSupportButton(event.getGuild())
                ).queue();
                if(Bot.isDebug()) return;
                TextChannel logChannel = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuild().getId()).getTextChannelById(ConfigManager.getSettingsConfig().getLogChannels().getError());
                embed.addField("URL", e.getMessage().split(" ")[1], false)
                        .setAuthor(event.getUser().getAsTag(), event.getUser().getAvatarUrl())
                        .setDescription(" ");
                logChannel.sendMessageEmbeds(embed.build()).queue();
            }
        });
        thread.start();
    }

}
