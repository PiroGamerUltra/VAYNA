package dev.piste.vayna.util.translations;

import dev.piste.vayna.mongodb.GuildSetting;
import net.dv8tion.jda.api.entities.Guild;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class LanguageManager {

    private static HashMap<String, Language> languageList;

    public static void loadLanguages() {
        languageList = new HashMap<>();
        loadLanguage("en-US");
        loadLanguage("de-DE");
    }

    private static void loadLanguage(String languageCode) {
        languageList.put(languageCode, new Language(languageCode));
    }

    public static Language getLanguage(Guild guild) {
        if(guild == null) {
            return languageList.get("en-US");
        }
        GuildSetting guildSetting = new GuildSetting(guild.getIdLong());
        return languageList.get(guildSetting.getLanguageCode());
    }

    public static Language getLanguage() {
        return languageList.get("en-US");
    }

    public static Collection<Language> getLanguages() {
        return languageList.values();
    }

}
