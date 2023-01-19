package dev.piste.vayna.api.riotgames;

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

public class AccountAPI {

    private static final String riotApiKey = (String) TokensConfig.readToken(TokenType.RIOT_API_KEY);
    private static CloseableHttpClient httpClient = HttpClients.createDefault();
    public static String[] getByPuuid(String puuid) {
        HttpGet httpGet = new HttpGet("https://europe.api.riotgames.com/riot/account/v1/accounts/by-puuid/" + puuid);
        httpGet.addHeader("X-Riot-Token", riotApiKey);
        httpGet.addHeader("User-Agent", "Mozilla/5.0");
        String[] result = new String[2];
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject account = (JSONObject) new JSONParser().parse(responseString);
            result[0] = (String) account.get("gameName");
            result[1] = (String) account.get("tagLine");
        } catch (IOException | org.json.simple.parser.ParseException | ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

}
