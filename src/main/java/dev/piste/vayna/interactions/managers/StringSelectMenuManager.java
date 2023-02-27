package dev.piste.vayna.interactions.managers;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.interactions.selectmenus.string.BundleSelectMenu;
import dev.piste.vayna.interactions.selectmenus.string.HistorySelectMenu;
import dev.piste.vayna.interactions.selectmenus.string.LanguageSelectMenu;
import dev.piste.vayna.interactions.selectmenus.string.SettingsSelectMenu;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

import java.io.IOException;
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
        addStringSelectMenu(new BundleSelectMenu());
    }

    private static void addStringSelectMenu(StringSelectMenu stringSelectMenu) {
        stringSelectMenus.put(stringSelectMenu.getName(), stringSelectMenu);
    }

    public static void perform(StringSelectInteractionEvent event) throws HttpErrorException, IOException, InterruptedException {
        stringSelectMenus.get(event.getSelectMenu().getId()).perform(event);
    }

}
