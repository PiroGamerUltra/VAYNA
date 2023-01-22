package dev.piste.vayna.config;

import java.io.FileReader;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class TokensConfig {

    private final String publicBotToken;
    private final String developmentBotToken;
    private final String mongodbToken;
    private final String riotApiToken;
    private final String henrikApiToken;

    public TokensConfig() {
        JSONObject jsonObject = new JSONObject();
        try (FileReader reader = new FileReader("tokens.json")) {
            jsonObject = (JSONObject) new JSONParser().parse(reader);
        } catch (ParseException | IOException | NullPointerException e) {
            e.printStackTrace();
        }
        JSONObject botKeysObject = (JSONObject) jsonObject.get("bot");
        this.developmentBotToken = (String) botKeysObject.get("development");
        this.publicBotToken = (String) botKeysObject.get("public");
        this.mongodbToken = (String) jsonObject.get("mongodb");
        this.riotApiToken = (String) jsonObject.get("riot_api");
        this.henrikApiToken = (String) jsonObject.get("henrik_api");
    }

    public String getPublicBotToken() {
        return publicBotToken;
    }

    public String getDevelopmentBotToken() {
        return developmentBotToken;
    }

    public String getMongodbToken() {
        return mongodbToken;
    }

    public String getRiotApiToken() {
        return riotApiToken;
    }

    public String getHenrikApiToken() {
        return henrikApiToken;
    }
}
