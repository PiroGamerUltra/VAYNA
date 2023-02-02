package dev.piste.vayna.manager;

import dev.piste.vayna.stringselectmenus.LanguageSelectMenu;
import dev.piste.vayna.stringselectmenus.SettingsSelectMenu;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

import java.util.HashMap;

/**
 * @author Piste | https://github.com/zPiste
 */
public class StringSelectMenuManager {

    private static final HashMap<String, StringSelectMenu> stringSelectMenus = new HashMap<>();

    public static void registerStringSelectMenus() {
        addStringSelectMenu(new SettingsSelectMenu());
        addStringSelectMenu(new LanguageSelectMenu());
    }

    private static void addStringSelectMenu(StringSelectMenu stringSelectMenu) {
        stringSelectMenus.put(stringSelectMenu.getName(), stringSelectMenu);
    }

    public static void perform(StringSelectInteractionEvent event) {
        Thread thread = new Thread(() -> stringSelectMenus.get(event.getSelectMenu().getId()).perform(event));
        thread.start();
    }

}
