package dev.piste.vayna.api;

import com.fasterxml.jackson.databind.JsonNode;
import dev.piste.vayna.Bot;
import dev.piste.vayna.config.TokensConfig;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import java.io.IOException;

public class HttpRequest {

    public static JsonNode doRiotApiRequest(String uri) {
        return getJsonNode("X-Riot-Token", TokensConfig.getRiotApiToken(), uri);
    }

    public static JsonNode doHenrikApiRequest(String uri) {
        return getJsonNode("Authorization", TokensConfig.getHenrikApiToken(), uri);
    }

    public static JsonNode doValorantApiRequest(String uri) {
        return getJsonNode(null, null, uri);
    }

    private static JsonNode getJsonNode(String headerName, String headerValue, String uri) {
        HttpGet httpGet = new HttpGet(uri);
        if(headerName != null && headerValue != null) httpGet.addHeader(headerName, headerValue);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            return Bot.getObjectMapper().readTree(EntityUtils.toString(response.getEntity()));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
