package dev.piste.vayna.util.selectmenus;

import dev.piste.vayna.util.Language;
import dev.piste.vayna.util.LanguageManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

/**
 * @author Piste | https://github.com/zPiste
 */
public class SelectMenus {

    public static StringSelectMenu getSettingsSelectMenu(Guild guild) {

        Language language = LanguageManager.getLanguage(guild);

        return StringSelectMenu.create("settings")
                .setPlaceholder(language.getTranslation("command-settings-selectmenu-placeholder"))
                .setMinValues(1)
                .setMaxValues(1)
                .addOption(language.getTranslation("command-settings-selectmenu-option-1"), "language", Emoji.fromUnicode("\uD83D\uDDE3️"))
                .build();
    }

}
