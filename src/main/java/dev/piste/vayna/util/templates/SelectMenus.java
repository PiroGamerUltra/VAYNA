package dev.piste.vayna.util.templates;

import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
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
                .addOption(language.getTranslation("command-settings-selectmenu-option-1"), "language", Emoji.fromUnicode("\uD83D\uDDE3️"))
                .build();
    }

}
