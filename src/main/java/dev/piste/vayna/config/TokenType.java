package dev.piste.vayna.config;

public enum TokenType {

    PUBLIC("public"),
    DEVELOPMENT("development"),
    MONGODB("mongodb"),

    RIOT_API_KEY("riot_api_key");

    private final String text;

    TokenType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
