package dev.piste.vayna.config.translations.commands;

import dev.piste.vayna.config.translations.commands.stats.Errors;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Stats {

    private String title;
    private String description;
    private String level;
    private String region;
    private String connection;
    private String rank;
    private Errors errors;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLevel() {
        return level;
    }

    public String getRegion() {
        return region;
    }

    public String getConnection() {
        return connection;
    }

    public String getRank() {
        return rank;
    }

    public Errors getErrors() {
        return errors;
    }
}
