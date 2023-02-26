package dev.piste.vayna.interactions.managers;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.interactions.selectmenus.string.HistorySelectMenu;
import dev.piste.vayna.interactions.selectmenus.string.LanguageSelectMenu;
import dev.piste.vayna.interactions.selectmenus.string.SettingsSelectMenu;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.ErrorMessages;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

import java.util.HashMap;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class StringSelectMenuManager {

    private static final HashMap<String, StringSelectMenu> stringSelectMenus = new HashMap<>();

    public static void registerStringSelectMenus() {
        addStringSelectMenu(new SettingsSelectMenu());
        addStringSelectMenu(new LanguageSelectMenu());
        addStringSelectMenu(new HistorySelectMenu());
    }

    private static void addStringSelectMenu(StringSelectMenu stringSelectMenu) {
        stringSelectMenus.put(stringSelectMenu.getName(), stringSelectMenu);
    }

    public static void perform(StringSelectInteractionEvent event) {
        Thread thread = new Thread(() -> {
            try {
                stringSelectMenus.get(event.getSelectMenu().getId()).perform(event);
            } catch (HttpErrorException e) {
            Embed embed = ErrorMessages.getStatusCodeErrorEmbed(event.getGuild(), event.getUser(), e);
            event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                    Buttons.getSupportButton(event.getGuild())
            ).queue();
            if(Bot.isDebug()) return;
            TextChannel logChannel = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuildId()).getTextChannelById(ConfigManager.getSettingsConfig().getLogChannelIds().getError());
            embed.addField("URL", e.getMessage().split(" ")[1], false)
                    .setAuthor(event.getUser().getAsTag(), event.getUser().getAvatarUrl())
                    .setDescription(" ");
            logChannel.sendMessageEmbeds(embed.build()).queue();
        }
        });
        thread.start();
    }

}
