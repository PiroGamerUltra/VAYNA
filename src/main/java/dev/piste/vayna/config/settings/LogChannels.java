package dev.piste.vayna.config.settings;

// GSON CLASS
@SuppressWarnings("ALL")
public class LogChannels {

    private long guild;
    private long feedback;
    private long error;

    public long getGuild() {
        return guild;
    }

    public long getFeedback() {
        return feedback;
    }

    public long getError() {
        return error;
    }
}
