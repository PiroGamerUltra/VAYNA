package dev.piste.vayna.util.selectmenus;

import dev.piste.vayna.util.TranslationManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

/**
 * @author Piste | https://github.com/zPiste
 */
public class SelectMenus {

    public static StringSelectMenu getSettingsSelectMenu(Guild guild) {

        TranslationManager translation = TranslationManager.getTranslation(guild);

        return StringSelectMenu.create("settings")
                .setPlaceholder(translation.getTranslation("command-settings-selectmenu-placeholder"))
                .setMinValues(1)
                .setMaxValues(1)
                .addOption(translation.getTranslation("command-settings-selectmenu-option-1"), "language", Emoji.fromUnicode("\uD83D\uDDE3️"))
                .build();
    }

}
