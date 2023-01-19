package dev.piste.vayna.mongodb;

public enum Collection {

    LINKED_ACCOUNTS("linked-accounts"),
    BOT_STATS("bot-stats"),

    AUTH_KEYS("auth-keys");

    private final String text;

    Collection(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
