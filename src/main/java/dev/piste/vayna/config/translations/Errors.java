package dev.piste.vayna.config.translations;

import dev.piste.vayna.config.translations.error.API;
import dev.piste.vayna.config.translations.error.RateLimit;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Errors {

    private API api;
    private RateLimit rateLimit;

    public API getApi() {
        return api;
    }

    public RateLimit getRateLimit() {
        return rateLimit;
    }
}