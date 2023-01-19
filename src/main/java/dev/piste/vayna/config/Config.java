package dev.piste.vayna.config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Config {

    public static String readSetting(ConfigSetting configSetting) {
        final String key = configSetting.toString();
        try (FileReader reader = new FileReader("config.json")) {
            JSONObject config = (JSONObject) new JSONParser().parse(reader);
            return (String) config.get(key);
        } catch (ParseException | IOException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

}
