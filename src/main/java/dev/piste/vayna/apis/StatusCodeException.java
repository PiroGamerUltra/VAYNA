package dev.piste.vayna.apis;

/**
 * @author Piste | https://github.com/zPiste
 */
public class StatusCodeException extends Exception {

    public StatusCodeException(String message) {
        super(message);
    }

    public StatusCodeException() {
        super();
    }

}
