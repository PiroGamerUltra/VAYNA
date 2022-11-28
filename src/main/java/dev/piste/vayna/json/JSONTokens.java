package dev.piste.vayna.json;

import dev.piste.vayna.util.TokenType;

import java.io.FileNotFoundException;
import java.io.FileReader;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class JSONTokens {

    public static Object readToken(TokenType tokenType) {

        final String key = tokenType.toString();

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("tokens.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject tokens = (JSONObject) obj;
            return tokens.get(key);

        } catch (ParseException | IOException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

}
