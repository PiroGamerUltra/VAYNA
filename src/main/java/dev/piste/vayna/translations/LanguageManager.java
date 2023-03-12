package dev.piste.vayna.translations;

import dev.piste.vayna.mongodb.GuildSetting;
import net.dv8tion.jda.api.entities.Guild;

import java.util.*;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class LanguageManager {

    private static final Map<String, Language> LANGUAGES = new HashMap<>();
    private static final String DEFAULT_LOCALE = "en-US";

    /**
     * Loads all available languages.
     */
    public static void loadLanguages() {
        loadLanguage("en-US");
        loadLanguage("de-DE");
    }

    /**
     * Loads a language from the translations folder.
     *
     * @param locale The locale identifier of the language to load.
     */
    private static void loadLanguage(String locale) {
        LANGUAGES.put(locale, new Language(locale));
    }

    /**
     * Returns the language object for a given guild.
     *
     * @param guild The guild for which to get the language.
     * @return The language object for the guild.
     */
    public static Language getLanguage(Guild guild) {
        if (guild == null) return getDefaultLanguage();
        return getLanguage(new GuildSetting(guild.getIdLong()).getLanguageCode());
    }

    /**
     * Returns the default language object.
     *
     * @return The default language object.
     */
    public static Language getDefaultLanguage() {
        return getLanguage(DEFAULT_LOCALE);
    }

    /**
     * Returns the language object for a given locale identifier.
     *
     * @param locale The locale identifier for which to get the language object.
     * @return The language object for the given locale identifier, or default language if the locale is not found.
     */
    private static Language getLanguage(String locale) {
        return LANGUAGES.get(locale) != null ? LANGUAGES.get(locale) : LANGUAGES.get(DEFAULT_LOCALE);
    }

    /**
     * Returns a list of all loaded languages.
     *
     * @return The collection of all loaded languages.
     */
    public static Collection<Language> getLanguages() {
        return Collections.unmodifiableCollection(LANGUAGES.values());
    }

}