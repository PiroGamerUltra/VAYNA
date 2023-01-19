package dev.piste.vayna.config;

public enum TokenType {

    PUBLIC("public"),
    DEVELOPMENT("development"),
    MONGODB("mongodb");

    private final String text;

    TokenType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
