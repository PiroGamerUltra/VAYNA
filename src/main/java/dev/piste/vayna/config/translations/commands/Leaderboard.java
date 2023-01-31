package dev.piste.vayna.config.translations.commands;

import dev.piste.vayna.config.translations.commands.leaderboard.Errors;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Leaderboard {

    private String title;
    private String description;
    private Errors errors;

    public Errors getErrors() {
        return errors;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
