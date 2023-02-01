package dev.piste.vayna.config.tokens;

// GSON CLASS
@SuppressWarnings("ALL")
public class TokensConfig {

    private Bot bot;
    private MongoDbConfig mongodb;
    private Api api;

    public Bot getBot() {
        return bot;
    }

    public MongoDbConfig getMongodb() {
        return mongodb;
    }

    public Api getApi() {
        return api;
    }
}

