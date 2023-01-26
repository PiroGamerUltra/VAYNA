package dev.piste.vayna.config;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.piste.vayna.Bot;
import dev.piste.vayna.config.settings.SettingsConfig;
import dev.piste.vayna.config.tokens.TokensConfig;

import java.io.FileReader;
import java.io.IOException;

public class Configs {

    public static SettingsConfig getSettings() {
        try (FileReader reader = new FileReader("settings.json")) {
            SettingsConfig settingsConfig = Bot.getObjectMapper().readValue(Bot.getObjectMapper().readTree(reader).traverse(), new TypeReference<SettingsConfig>() {});
            return settingsConfig;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TokensConfig getTokens() {
        try (FileReader reader = new FileReader("tokens.json")) {
            TokensConfig tokensConfig = Bot.getObjectMapper().readValue(Bot.getObjectMapper().readTree(reader).traverse(), new TypeReference<TokensConfig>() {});
            return tokensConfig;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
