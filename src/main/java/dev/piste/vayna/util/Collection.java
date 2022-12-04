package dev.piste.vayna.util;

public enum Collection {

    ACCOUNTS("accounts"),
    BOT_STATS("bot-stats");

    private final String text;

    Collection(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
