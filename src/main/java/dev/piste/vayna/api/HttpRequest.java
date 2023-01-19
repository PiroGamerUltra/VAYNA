package dev.piste.vayna.api;

import dev.piste.vayna.config.TokenType;
import dev.piste.vayna.config.TokensConfig;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;

public class HttpRequest {

    public static JSONObject doHttpRequest(String uri) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader("X-Riot-Token", TokensConfig.readToken(TokenType.RIOT_API_KEY));
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(responseString);
            return jsonObject;
        } catch (IOException | org.json.simple.parser.ParseException | ParseException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

}
