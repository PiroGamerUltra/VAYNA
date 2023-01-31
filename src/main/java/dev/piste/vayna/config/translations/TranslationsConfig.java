package dev.piste.vayna.config.translations;

/**
 * @author Piste | https://github.com/zPiste
 */
// GSON CLASS
@SuppressWarnings("ALL")
public class TranslationsConfig {

    private String titlePrefix;
    private Language de;
    private Language en;

    public String getTitlePrefix() {
        return titlePrefix;
    }

    public Language getLanguage(String countryCode) {
        switch (countryCode) {
            case "en": return en;
            case "de": return de;
        }
        return null;
    }
}
