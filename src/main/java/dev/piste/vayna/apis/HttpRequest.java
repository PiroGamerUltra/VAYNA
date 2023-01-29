package dev.piste.vayna.apis;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.piste.vayna.config.Configs;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

public class HttpRequest {

    public JsonObject doRiotApiRequest(String uri) {
        return getJsonObject("X-Riot-Token", Configs.getTokens().getApi().getRiot(), uri);
    }

    public JsonObject doHenrikApiRequest(String uri) {
        return getJsonObject("Authorization", Configs.getTokens().getApi().getHenrik(), uri);
    }

    public JsonObject doValorantApiRequest(String uri) {
        return getJsonObject(null, null, uri);
    }

    private JsonObject getJsonObject(String headerName, String headerValue, String uri) {
        HttpGet httpGet = new HttpGet(uri);
        if(headerName != null && headerValue != null) httpGet.addHeader(headerName, headerValue);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            JsonElement jsonElement = JsonParser.parseString(httpClient.execute(httpGet, responseHandler));
            httpClient.close();
            return jsonElement.getAsJsonObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final HttpClientResponseHandler<String> responseHandler = classicHttpResponse -> EntityUtils.toString(classicHttpResponse.getEntity());
}
