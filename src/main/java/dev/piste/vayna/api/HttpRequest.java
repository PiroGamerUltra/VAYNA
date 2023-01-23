package dev.piste.vayna.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader("X-Riot-Token", TokensConfig.getRiotApiToken());
        return getJsonObject(httpGet);
    }

    public static JsonNode doHenrikApiRequest(String uri) {
        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader("Authorization", TokensConfig.getHenrikApiToken());
        return getJsonObject(httpGet);
    }

    private static JsonNode getJsonObject(HttpGet httpGet) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String responseString = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(responseString);
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
