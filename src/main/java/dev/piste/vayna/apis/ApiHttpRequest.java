package dev.piste.vayna.apis;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @author Piste | https://github.com/zPiste
 */
public class ApiHttpRequest {

    public JsonObject performHttpRequest(HttpRequest httpRequest) throws StatusCodeException {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(response.statusCode() == 200) {
            return JsonParser.parseString(response.body()).getAsJsonObject();
        } else {
            throw new StatusCodeException(response.statusCode() + " " + httpRequest.uri().toString());
        }

    }

}
