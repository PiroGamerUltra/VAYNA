package dev.piste.vayna.apis;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class HttpErrorException extends Exception {

    private final int statusCode;
    private final String uri;
    private final String requestMethod;

    public HttpErrorException(int statusCode, String uri, String requestMethod) {
        super("HTTP Error " + statusCode + " on " + requestMethod + " " + uri);
        this.statusCode = statusCode;
        this.uri = uri;
        this.requestMethod = requestMethod;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getUri() {
        return uri;
    }

    public String getRequestMethod() {
        return requestMethod;
    }
}
