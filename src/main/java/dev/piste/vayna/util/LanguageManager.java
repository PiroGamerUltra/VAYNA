package dev.piste.vayna.util;

import dev.piste.vayna.mongodb.GuildSetting;
import net.dv8tion.jda.api.entities.Guild;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author Piste | https://github.com/zPiste
 */
public class LanguageManager {

    private static final HashMap<String, Language> languageList = new HashMap<>();

    public static void loadLanguages() {
        loadLanguage("en-US");
        loadLanguage("de-DE");
        loadLanguage("ru-RU");
    }

    private static void loadLanguage(String languageCode) {
        languageList.put(languageCode, new Language(languageCode));
    }

    public static Language getLanguage(Guild guild) {
        if(guild == null) {
            return languageList.get("en-US");
        }
        GuildSetting guildSetting = new GuildSetting(guild.getIdLong());
        return languageList.get(guildSetting.getLanguage());
    }

    public static Language getLanguage() {
        return languageList.get("en-US");
    }

    public static Collection<Language> getLanguages() {
        return languageList.values();
    }

}
