package dev.piste.vayna.apis.officer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dev.piste.vayna.apis.RestClient;
import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.officer.gson.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class OfficerAPI {

    private final String BASE_URL = "https://valorant-api.com/v1";
    private final RestClient restClient = new RestClient(BASE_URL);

    // Agents
    public Agent getAgent(String uuid, String languageCode) throws HttpErrorException, IOException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/agents/%s?language=%s", uuid, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Agent.class);
    }

    public ArrayList<Agent> getAgents(String languageCode) throws HttpErrorException, IOException, InterruptedException {
        JsonArray jsonArray = restClient.doGet(String.format("/agents?isPlayableCharacter=true&language=%s", languageCode)).getAsJsonArray("data");
        return new Gson().fromJson(jsonArray, new TypeToken<ArrayList<Agent>>(){}.getType());
    }

    // Gamemodes
    public Gamemode getGamemode(String uuid, String languageCode) throws HttpErrorException, IOException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/gamemodes/%s?language=%s", uuid, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Gamemode.class);
    }

    // Queues
    public Queue getQueue(String uuid, String languageCode) throws HttpErrorException, IOException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/gamemodes/queues/%s?language=%s", uuid, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Queue.class);
    }

    public ArrayList<Queue> getQueues(String languageCode) throws HttpErrorException, IOException, InterruptedException {
        JsonArray jsonArray = restClient.doGet(String.format("/gamemodes/queues?language=%s", languageCode)).getAsJsonArray("data");
        return new Gson().fromJson(jsonArray, new TypeToken<ArrayList<Queue>>(){}.getType());
    }

    // Maps
    public Map getMap(String uuid, String languageCode) throws HttpErrorException, IOException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/maps/%s?language=%s", uuid, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Map.class);
    }

    public ArrayList<Map> getMaps(String languageCode) throws HttpErrorException, IOException, InterruptedException {
        JsonArray jsonArray = restClient.doGet(String.format("/maps?language=%s", languageCode)).getAsJsonArray("data");
        return new Gson().fromJson(jsonArray, new TypeToken<ArrayList<Map>>(){}.getType());
    }

    // Weapons
    public Weapon getWeapon(String uuid, String languageCode) throws HttpErrorException, IOException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/weapons/%s?language=%s", uuid, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Weapon.class);
    }

    public ArrayList<Weapon> getWeapons(String languageCode) throws HttpErrorException, IOException, InterruptedException {
        JsonArray jsonArray = restClient.doGet(String.format("/weapons?language=%s", languageCode)).getAsJsonArray("data");
        return new Gson().fromJson(jsonArray, new TypeToken<ArrayList<Weapon>>(){}.getType());
    }

    public Buddy getBuddy(String uuid, String languageCode) throws HttpErrorException, IOException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/buddies/%s?language=%s", uuid, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Buddy.class);
    }

    public Bundle getBundle(String uuid, String languageCode) throws HttpErrorException, IOException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/bundles/%s?language=%s", uuid, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Bundle.class);
    }

    public Playercard getPlayercard(String uuid, String languageCode) throws HttpErrorException, IOException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/playercards/%s?language=%s", uuid, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Playercard.class);
    }

    public Spray getSpray(String uuid, String languageCode) throws HttpErrorException, IOException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/sprays/%s?language=%s", uuid, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Spray.class);
    }

    public CompetitiveTier getCompetitiveTier(String languageCode) throws HttpErrorException, IOException, InterruptedException {
        final String CURRENT_SEASON_ID = "03621f52-342b-cf4e-4f86-9350a49c6d04";
        JsonObject jsonObject = restClient.doGet(String.format("/competitivetiers/%s?language=%s", CURRENT_SEASON_ID, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, CompetitiveTier.class);
    }

    public Skin getSkin(String uuid, String languageCode) throws HttpErrorException, IOException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/weapons/skins/%s?language=%s", uuid, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Skin.class);
    }

}
