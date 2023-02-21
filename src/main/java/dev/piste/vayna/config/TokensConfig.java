package dev.piste.vayna.config;

// GSON CLASS
@SuppressWarnings("ALL")
public class TokensConfig {

    private Bot bot;
    private MongoCredentials mongodb;
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
        private String vayna;
        private String development;

        public String getVayna() {
            return vayna;
        }

        public String getDevelopment() {
            return development;
        }
    }

    public static class MongoCredentials {
        private String host;
        private int port;
        private String username;
        private String password;
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
        private String riot;
        private String henrik;
        private String topGG;

        public String getRiot() {
            return riot;
        }

        public String getHenrik() {
            return henrik;
        }

        public String getTopGG() {
            return topGG;
        }
    }

}