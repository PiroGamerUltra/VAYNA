package dev.piste.vayna.translations;

import dev.piste.vayna.mongodb.GuildSetting;
import net.dv8tion.jda.api.entities.Guild;

import java.util.*;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class LanguageManager {

    private static final Map<String, Language> LANGUAGE_LIST = new HashMap<>();
    private static final String DEFAULT_LOCALE = "en-US";

    /**
     * Method to load all supported languages into the map.
     */
    public static void loadLanguages() {
        loadLanguage("en-US");
        loadLanguage("de-DE");
    }

    /**
     * Method to load a language object into the map.
     *
     * @param locale The locale identifier of the language to load.
     */
    private static void loadLanguage(String locale) {
        LANGUAGE_LIST.put(locale, new Language(locale));
    }

    /**
     * Method to get the language for a guild. If guild is null, returns default language.
     *
     * @param guild The guild for which to get the language.
     * @return The language for the guild.
     */
    public static Language getLanguage(Guild guild) {
        if (guild == null) {
            return getDefaultLanguage();
        }
        GuildSetting guildSetting = new GuildSetting(guild.getIdLong());
        return getLanguage(guildSetting.getLanguageCode());
    }

    /**
     * Method to get the default language.
     *
     * @return The default language.
     */
    public static Language getDefaultLanguage() {
        return getLanguage(DEFAULT_LOCALE);
    }

    /**
     * Method to get the language object for a given language code.
     *
     * @param locale The locale identifier for which to get the language object.
     * @return The language object for the given language code, or English if the language code is not found.
     */
    private static Language getLanguage(String locale) {
        Language language = LANGUAGE_LIST.get(locale);
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
