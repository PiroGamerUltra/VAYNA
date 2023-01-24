package dev.piste.vayna.api;

import com.fasterxml.jackson.databind.JsonNode;
import dev.piste.vayna.Bot;
import dev.piste.vayna.config.SettingsConfig;
import dev.piste.vayna.config.TokensConfig;
import dev.piste.vayna.embeds.ExceptionEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import java.io.IOException;

public class HttpRequest {

    public static JsonNode doRiotApiRequest(String uri) {
        HttpGet httpGet = new HttpGet("xt" + uri);
        httpGet.addHeader("X-Riot-Token", TokensConfig.getRiotApiToken());
        return getJsonNode(httpGet);
    }

    public static JsonNode doHenrikApiRequest(String uri) {
        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader("Authorization", TokensConfig.getHenrikApiToken());
        return getJsonNode(httpGet);
    }

    private static JsonNode getJsonNode(HttpGet httpGet) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            return Bot.getObjectMapper().readTree(EntityUtils.toString(response.getEntity()));
        } catch (IOException | ParseException e) {
            TextChannel exceptionChannel = Bot.getJDA().getGuildById(SettingsConfig.getSupportGuildId()).getTextChannelById(SettingsConfig.getExceptionLogChannelId());
            exceptionChannel.sendMessageEmbeds(ExceptionEmbed.getHttpRequestException(e.getMessage())).queue();
            throw new RuntimeException(e);
        }
    }

}
