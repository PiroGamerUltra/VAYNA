package dev.piste.vayna.apis;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.piste.vayna.config.Configs;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import java.io.IOException;

public class HttpRequest {

    public static JsonObject doRiotApiRequest(String uri) {
        return getJsonObject("X-Riot-Token", Configs.getTokens().getApi().getRiot(), uri);
    }

    public static JsonObject doHenrikApiRequest(String uri) {
        return getJsonObject("Authorization", Configs.getTokens().getApi().getHenrik(), uri);
    }

    public static JsonObject doValorantApiRequest(String uri) {
        return getJsonObject(null, null, uri);
    }

    private static JsonObject getJsonObject(String headerName, String headerValue, String uri) {
        HttpGet httpGet = new HttpGet(uri);
        if(headerName != null && headerValue != null) httpGet.addHeader(headerName, headerValue);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            JsonElement jsonElement = JsonParser.parseString(EntityUtils.toString(response.getEntity()));
            return jsonElement.getAsJsonObject();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
