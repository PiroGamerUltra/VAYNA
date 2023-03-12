package dev.piste.vayna.http.apis;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dev.piste.vayna.http.HttpErrorException;
import dev.piste.vayna.http.RestClient;
import dev.piste.vayna.http.models.officer.*;

import java.io.IOException;
import java.util.List;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class OfficerAPI {

    private final RestClient restClient;

    private final String CURRENT_COMPETITIVETIER_ID = "03621f52-342b-cf4e-4f86-9350a49c6d04";

    public OfficerAPI() {
        String BASE_URL = "https://valorant-api.com/v1";
        restClient = new RestClient(BASE_URL);
    }

    public Season getSeason(String uuid, String languageCode) throws HttpErrorException, IOException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/seasons/%s?language=%s", uuid, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Season.class);
    }

    public List<Season> getSeasons(String languageCode) throws HttpErrorException, IOException, InterruptedException {
        JsonArray jsonArray = restClient.doGet(String.format("/seasons?language=%s", languageCode)).getAsJsonArray("data");
        return new Gson().fromJson(jsonArray, new TypeToken<List<Season>>(){}.getType());
    }

    public Theme getTheme(String id, String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/themes/%s?language=%s", id, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Theme.class);
    }

    public List<Theme> getThemes(String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonArray jsonArray = restClient.doGet(String.format("/themes?language=%s", languageCode)).getAsJsonArray("data");
        return new Gson().fromJson(jsonArray, new TypeToken<List<Theme>>(){}.getType());
    }

    public Agent getAgent(String id, String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/agents/%s?language=%s", id, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Agent.class);
    }

    public List<Agent> getAgents(String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonArray jsonArray = restClient.doGet(String.format("/agents?language=%s&isPlayableCharacter=true", languageCode)).getAsJsonArray("data");
        return new Gson().fromJson(jsonArray, new TypeToken<List<Agent>>(){}.getType());
    }

    public PlayerCard getPlayerCard(String id, String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/playercards/%s?language=%s", id, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, PlayerCard.class);
    }

    public List<PlayerCard> getPlayerCards(String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonArray jsonArray = restClient.doGet(String.format("/playercards?language=%s", languageCode)).getAsJsonArray("data");
        return new Gson().fromJson(jsonArray, new TypeToken<List<PlayerCard>>(){}.getType());
    }

    public PlayerTitle getPlayerTitle(String id, String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/playertitles/%s?language=%s", id, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, PlayerTitle.class);
    }

    public List<PlayerTitle> getPlayerTitles(String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonArray jsonArray = restClient.doGet(String.format("/playertitles?language=%s", languageCode)).getAsJsonArray("data");
        return new Gson().fromJson(jsonArray, new TypeToken<List<PlayerTitle>>(){}.getType());
    }

    public Map getMap(String id, String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/maps/%s?language=%s", id, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Map.class);
    }

    public List<Map> getMaps(String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonArray jsonArray = restClient.doGet(String.format("/maps?language=%s", languageCode)).getAsJsonArray("data");
        return new Gson().fromJson(jsonArray, new TypeToken<List<Map>>(){}.getType());
    }

    public Queue getQueue(String id, String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/gamemodes/queues/%s?language=%s", id, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Queue.class);
    }

    public List<Queue> getQueues(String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonArray jsonArray = restClient.doGet(String.format("/gamemodes/queues?language=%s", languageCode)).getAsJsonArray("data");
        return new Gson().fromJson(jsonArray, new TypeToken<List<Queue>>(){}.getType());
    }

    public Weapon getWeapon(String id, String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/weapons/%s?language=%s", id, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Weapon.class);
    }

    public List<Weapon> getWeapons(String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonArray jsonArray = restClient.doGet(String.format("/weapons?language=%s", languageCode)).getAsJsonArray("data");
        return new Gson().fromJson(jsonArray, new TypeToken<List<Weapon>>(){}.getType());
    }

    public Gear getGear(String id, String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/gear/%s?language=%s", id, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Gear.class);
    }

    public List<Gear> getGear(String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonArray jsonArray = restClient.doGet(String.format("/gear?language=%s", languageCode)).getAsJsonArray("data");
        return new Gson().fromJson(jsonArray, new TypeToken<List<Gear>>(){}.getType());
    }

    public GameMode getGameMode(String id, String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/gamemodes/%s?language=%s", id, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, GameMode.class);
    }

    public List<GameMode> getGameModes(String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonArray jsonArray = restClient.doGet(String.format("/gamemodes?language=%s", languageCode)).getAsJsonArray("data");
        return new Gson().fromJson(jsonArray, new TypeToken<List<GameMode>>(){}.getType());
    }

    public Bundle getBundle(String id, String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/bundles/%s?language=%s", id, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Bundle.class);
    }

    public List<Bundle> getBundles(String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonArray jsonArray = restClient.doGet(String.format("/bundles?language=%s", languageCode)).getAsJsonArray("data");
        return new Gson().fromJson(jsonArray, new TypeToken<List<Bundle>>(){}.getType());
    }

    public Rank getRank(int id, String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/competitivetiers/" + CURRENT_COMPETITIVETIER_ID + "?language=%s", languageCode)).getAsJsonObject("data");
        List<Rank> ranks = new Gson().fromJson(jsonObject.getAsJsonArray("tiers"), new TypeToken<List<Rank>>(){}.getType());
        for(Rank rank : ranks) {
            if(rank.getId() == id) {
                return rank;
            }
        }
        return null;
    }

    public List<Rank> getRanks(String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/competitivetiers/" + CURRENT_COMPETITIVETIER_ID + "?language=%s", languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject.getAsJsonArray("tiers"), new TypeToken<List<Rank>>(){}.getType());
    }

    public Buddy getBuddy(String id, String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/buddies/%s?language=%s", id, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Buddy.class);
    }

    public List<Buddy> getBuddies(String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonArray jsonArray = restClient.doGet(String.format("/buddies?language=%s", languageCode)).getAsJsonArray("data");
        return new Gson().fromJson(jsonArray, new TypeToken<List<Buddy>>() {
        }.getType());
    }

    public Spray getSpray(String id, String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/sprays/%s?language=%s", id, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Spray.class);
    }

    public List<Spray> getSprays(String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonArray jsonArray = restClient.doGet(String.format("/sprays?language=%s", languageCode)).getAsJsonArray("data");
        return new Gson().fromJson(jsonArray, new TypeToken<List<Spray>>() {
        }.getType());
    }

    public Weapon.Skin getSkin(String id, String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/weapons/skins/%s?language=%s", id, languageCode)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, Weapon.Skin.class);
    }

    public List<Weapon.Skin> getSkins(String languageCode) throws IOException, HttpErrorException, InterruptedException {
        JsonArray jsonArray = restClient.doGet(String.format("/weapons/skins?language=%s", languageCode)).getAsJsonArray("data");
        return new Gson().fromJson(jsonArray, new TypeToken<List<Weapon.Skin>>() {
        }.getType());
    }

}
