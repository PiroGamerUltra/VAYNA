package dev.piste.vayna.config;

import com.google.gson.Gson;
import dev.piste.vayna.config.settings.SettingsConfig;
import dev.piste.vayna.config.tokens.TokensConfig;
import dev.piste.vayna.config.translations.TranslationsConfig;

import java.io.FileReader;
import java.io.IOException;

public class Configs {

    public static SettingsConfig getSettings() {
        try (FileReader reader = new FileReader("settings.json")) {
            return new Gson().fromJson(reader, SettingsConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TokensConfig getTokens() {
        try (FileReader reader = new FileReader("tokens.json")) {
            return new Gson().fromJson(reader, TokensConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TranslationsConfig getTranslations() {
        try (FileReader reader = new FileReader("translations.json")) {
            return new Gson().fromJson(reader, TranslationsConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
