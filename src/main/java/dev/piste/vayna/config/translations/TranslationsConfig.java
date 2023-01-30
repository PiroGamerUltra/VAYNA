package dev.piste.vayna.config.translations;

/**
 * @author Piste | https://github.com/zPiste
 */
// GSON CLASS
@SuppressWarnings("ALL")
public class TranslationsConfig {

    private Language de;
    private Language en;

    public Language getLanguage(String countryCode) {
        switch (countryCode) {
            case "en": return en;
            case "de": return de;
        }
        return null;
    }
}
