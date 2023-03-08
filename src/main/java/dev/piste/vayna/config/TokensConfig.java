package dev.piste.vayna.config;

import com.google.gson.annotations.SerializedName;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("ALL")
public class TokensConfig {

    @SerializedName("bot")
    private Bot bot;
    @SerializedName("mongodb")
    private MongoCredentials mongodb;
    @SerializedName("apiKeys")
    private APIKeys apiKeys;

    public Bot getBot() {
        return bot;
    }

    public MongoCredentials getMongoCreds() {
        return mongodb;
    }

    public APIKeys getApiKeys() {
        return apiKeys;
    }

    public static class Bot {

        @SerializedName("vayna")
        private String vayna;
        @SerializedName("development")
        private String development;

        public String getVayna() {
            return vayna;
        }

        public String getDevelopment() {
            return development;
        }

    }

    public static class MongoCredentials {

        @SerializedName("host")
        private String host;
        @SerializedName("port")
        private int port;
        @SerializedName("username")
        private String username;
        @SerializedName("password")
        private String password;
        @SerializedName("authDb")
        private String authDb;

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getAuthDb() {
            return authDb;
        }

    }

    public static class APIKeys {

        @SerializedName("riotGames")
        private String riotGames;
        @SerializedName("henrik")
        private String henrik;
        @SerializedName("topGG")
        private String topGG;

        public String getRiotGames() {
            return riotGames;
        }

        public String getHenrik() {
            return henrik;
        }

        public String getTopGG() {
            return topGG;
        }

    }

}