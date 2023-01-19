package dev.piste.vayna.config;

import java.io.FileReader;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class TokensConfig {

    public static Object readToken(TokenType tokenType) {
        final String key = tokenType.toString();
        try (FileReader reader = new FileReader("tokens.json")) {
            JSONObject tokens = (JSONObject) new JSONParser().parse(reader);
            return tokens.get(key);
        } catch (ParseException | IOException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

}
