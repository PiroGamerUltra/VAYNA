package dev.piste.vayna.apis;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class RestClient {

    private final String baseUrl;
    private final HttpClient httpClient;
    private final HttpRequest.Builder httpRequestBuilder;

    public RestClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newHttpClient();
        this.httpRequestBuilder = HttpRequest.newBuilder();
        this.httpRequestBuilder.header("Content-Type", "application/json");
    }

    public RestClient appendHeader(String key, String value) {
        this.httpRequestBuilder.header(key, value);
        return this;
    }

    public JsonObject doGet(String path) throws HttpErrorException {
        HttpRequest httpRequest = httpRequestBuilder
                .uri(URI.create(baseUrl + path))
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200) {
                return JsonParser.parseString(response.body()).getAsJsonObject();
            } else {
                throw new HttpErrorException(response.statusCode(), httpRequest.uri().toString(), httpRequest.method());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
