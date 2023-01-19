package dev.piste.vayna.config;

public enum ConfigSetting {

    VERSION("version"),
    WEBSITE_URI("website_uri");

    private final String text;

    ConfigSetting(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
