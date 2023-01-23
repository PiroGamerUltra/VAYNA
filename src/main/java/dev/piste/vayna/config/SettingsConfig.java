package dev.piste.vayna.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;

public class SettingsConfig {

    public static String getVersion() {
        return getJsonNode().get("version").asText();
    }

    public static String getWebsiteUri() {
        return getJsonNode().get("websiteUri").asText();
    }

    public static long getSupportGuildId() {
        return getJsonNode().path("supportGuild").get("id").asLong();
    }

    public static String getSupportGuildInviteLink() {
        return getJsonNode().path("supportGuild").get("inviteLink").asText();
    }

    public static long getGuildJoinActivitiesChannelId() {
        return getJsonNode().path("guildActivitiesChannel").get("join").asLong();
    }

    public static long getGuildLeaveActivitiesChannelId() {
        return getJsonNode().path("guildActivitiesChannel").get("leave").asLong();
    }

    public static long getGuildCountChannelId() {
        return getJsonNode().path("botStatsChannel").get("guild").asLong();
    }

    public static String getGuildCountChannelName() {
        return getJsonNode().path("botStatsChannel").get("guildName").asText();
    }

    public static long getConnectionCountChannelId() {
        return getJsonNode().path("botStatsChannel").get("connection").asLong();
    }

    public static String getConnectionCountChannelName() {
        return getJsonNode().path("botStatsChannel").get("connectionName").asText();
    }

    private static JsonNode getJsonNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try (FileReader reader = new FileReader("settings.json")) {
            jsonNode = objectMapper.readTree(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonNode;
    }
}
