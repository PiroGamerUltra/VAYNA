package dev.piste.vayna.translations;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class Language {

    private static final Gson GSON = new Gson();

    private final Map<String, String> translations;

    protected Language(String languageCode) {
        Map<String, String> loadedTranslations;
        try {
            String json = Files.readString(Paths.get("translations", languageCode + ".json"));
            loadedTranslations = GSON.fromJson(json, new TypeToken<HashMap<String, String>>() {}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.translations = Collections.unmodifiableMap(loadedTranslations);
    }

    /**
     * Retrieves the translation for the given key.
     * @param key the key to retrieve the translation for
     * @return the translation for the given key, or "No translation found" if the key is not found
     */
    public String getTranslation(String key) {
        return translations.getOrDefault(key, "No translation found");
    }

    /**
     * Retrieves the language code for this language.
     * @return the language code
     */
    public String getLanguageCode() {
        return translations.get("language-code");
    }

    /**
     * Retrieves the prefix to use for embed titles in this language.
     * @return the embed title prefix
     */
    public String getEmbedTitlePrefix() {
        return translations.get("embed-title-prefix");
    }

}
