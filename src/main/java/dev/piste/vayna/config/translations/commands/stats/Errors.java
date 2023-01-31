package dev.piste.vayna.config.translations.commands.stats;

import dev.piste.vayna.config.translations.commands.stats.errors.Error;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Errors {

    private Error riotId;
    private Error region;
    private Error noConnectionSelf;
    private Error noConnection;
    private Error privateAccount;

    public Error getRiotId() {
        return riotId;
    }

    public Error getRegion() {
        return region;
    }

    public Error getNoConnectionSelf() {
        return noConnectionSelf;
    }

    public Error getNoConnection() {
        return noConnection;
    }

    public Error getPrivateAccount() {
        return privateAccount;
    }
}
