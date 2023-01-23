package dev.piste.vayna.config;

import java.io.FileReader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class TokensConfig {


    public static String getPublicBotToken() {
        return getJsonNode().path("bot").get("public").asText();
    }

    public static String getDevelopmentBotToken() {
        return getJsonNode().path("bot").get("development").asText();
    }

    public static String getMongodbToken() {
        return getJsonNode().get("mongodb").asText();
    }

    public static String getRiotApiToken() {
        return getJsonNode().path("api").get("riot").asText();
    }

    public static String getHenrikApiToken() {
        return getJsonNode().path("api").get("henrik").asText();
    }

    private static JsonNode getJsonNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try (FileReader reader = new FileReader("tokens.json")) {
            jsonNode = objectMapper.readTree(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonNode;
    }
}
