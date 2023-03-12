package dev.piste.vayna.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
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

    public RestClient addHeader(String key, String value) {
        this.httpRequestBuilder.header(key, value);
        return this;
    }

    public JsonObject doGet(String path) throws HttpErrorException, IOException, InterruptedException {
        HttpRequest httpRequest = httpRequestBuilder
                .uri(URI.create(baseUrl + path))
                .GET()
                .build();
        return sendRequest(httpRequest);
    }

    public JsonObject doPost(String path, String body) throws HttpErrorException, IOException, InterruptedException {
        HttpRequest httpRequest = httpRequestBuilder
                .uri(URI.create(baseUrl + path))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return sendRequest(httpRequest);
    }

    private JsonObject sendRequest(HttpRequest httpRequest) throws HttpErrorException, IOException, InterruptedException {
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if(HttpStatus.valueOf(response.statusCode()).isError()) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonObject jsonObject = (JsonObject) JsonParser.parseString(response.body());
                StringWriter bodyWriter = new StringWriter();
                JsonWriter jsonWriter = new JsonWriter(bodyWriter);
                jsonWriter.setIndent("  ");
                gson.toJson(jsonObject, jsonWriter);
                throw new HttpErrorException(response.statusCode(), bodyWriter.toString(), httpRequest.uri().toString(), httpRequest.method());
            }
        return JsonParser.parseString(response.body()).getAsJsonObject();
    }
}
