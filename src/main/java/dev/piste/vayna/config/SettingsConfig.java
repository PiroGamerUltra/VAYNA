package dev.piste.vayna.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;

public class SettingsConfig {

    public static String getVersion() {
        return getJsonNode().get("version").asText();
    }

    public static String getWebsiteUri() {
        return getJsonNode().get("website_uri").asText();
    }

    private static JsonNode getJsonNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try (FileReader reader = new FileReader("settings.json")) {
            jsonNode = objectMapper.readTree(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonNode;
    }
}
