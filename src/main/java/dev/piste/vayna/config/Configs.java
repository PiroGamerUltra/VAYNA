package dev.piste.vayna.config;

import com.google.gson.Gson;
import dev.piste.vayna.config.settings.SettingsConfig;
import dev.piste.vayna.config.tokens.TokensConfig;

import java.io.FileReader;
import java.io.IOException;

public class Configs {

    public static SettingsConfig getSettings() {
        try (FileReader reader = new FileReader("settings.json")) {
            SettingsConfig settingsConfig = new Gson().fromJson(reader, SettingsConfig.class);
            return settingsConfig;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TokensConfig getTokens() {
        try (FileReader reader = new FileReader("tokens.json")) {
            TokensConfig tokensConfig = new Gson().fromJson(reader, TokensConfig.class);
            return tokensConfig;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
