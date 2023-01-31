package dev.piste.vayna.config.translations.commands.leaderboard;

import dev.piste.vayna.config.translations.commands.leaderboard.errors.Error;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Errors {

    private Error privateChannel;
    private Error noPlayersDisplayable;

    public Error getPrivateChannel() {
        return privateChannel;
    }

    public Error getNoPlayersDisplayable() {
        return noPlayersDisplayable;
    }
}
