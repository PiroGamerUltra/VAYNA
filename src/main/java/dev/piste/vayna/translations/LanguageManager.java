package dev.piste.vayna.translations;

import dev.piste.vayna.mongodb.GuildSetting;
import net.dv8tion.jda.api.entities.Guild;

import java.util.*;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class LanguageManager {

    // Map to store the language objects
    private static final Map<String, Language> LANGUAGE_LIST = new HashMap<>();

    // Load the supported languages
    public static void loadLanguages() {
        loadLanguage("en-US");
        loadLanguage("de-DE");
    }

    /**
     * Method to load a language object into the map.
     *
     * @param languageCode The language code of the language to load.
     */
    private static void loadLanguage(String languageCode) {
        LANGUAGE_LIST.put(languageCode, new Language(languageCode));
    }

    /**
     * Method to get the language for a guild. If guild is null, returns English.
     *
     * @param guild The guild for which to get the language.
     * @return The language for the guild.
     */
    public static Language getLanguage(Guild guild) {
        if (guild == null) {
            return getLanguage("en-US");
        }
        GuildSetting guildSetting = new GuildSetting(guild.getIdLong());
        return getLanguage(guildSetting.getLanguageCode());
    }

    /**
     * Method to get the default language (English).
     *
     * @return The default language (English).
     */
    public static Language getLanguage() {
        return getLanguage("en-US");
    }

    /**
     * Method to get the language object for a given language code.
     *
     * @param languageCode The language code for which to get the language object.
     * @return The language object for the given language code, or English if the language code is not found.
     */
    private static Language getLanguage(String languageCode) {
        Language language = LANGUAGE_LIST.get(languageCode);
        return language != null ? language : LANGUAGE_LIST.get("en-US");
    }

    /**
     * Method to get the collection of all supported languages.
     *
     * @return The collection of all supported languages.
     */
    public static Collection<Language> getLanguages() {
        return Collections.unmodifiableCollection(LANGUAGE_LIST.values());
    }

}
