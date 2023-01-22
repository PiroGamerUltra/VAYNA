package dev.piste.vayna.config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class SettingsConfig {

    private final String version;

    private final String websiteUri;

    public SettingsConfig() {
        JSONObject jsonObject = new JSONObject();
        try (FileReader reader = new FileReader("settings.json")) {
            jsonObject = (JSONObject) new JSONParser().parse(reader);
        } catch (ParseException | IOException | NullPointerException e) {
            e.printStackTrace();
        }
        this.version = (String) jsonObject.get("version");
        this.websiteUri = (String) jsonObject.get("website_uri");
    }

    public String getVersion() {
        return version;
    }

    public String getWebsiteUri() {
        return websiteUri;
    }
}
