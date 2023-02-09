package dev.piste.vayna.config;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;

public class ConfigManager {

    private static SettingsConfig settingsConfig;
    private static TokensConfig tokensConfig;

    public static void loadConfigs() {
        try {
            settingsConfig = new Gson().fromJson(new FileReader("settings.json"), SettingsConfig.class);
            tokensConfig = new Gson().fromJson(new FileReader("tokens.json"), TokensConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SettingsConfig getSettingsConfig() {
        return settingsConfig;
    }

    public static TokensConfig getTokensConfig() {
        return tokensConfig;
    }

}
