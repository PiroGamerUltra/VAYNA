package dev.piste.vayna.apis.henrik;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dev.piste.vayna.apis.HttpRequest;
import dev.piste.vayna.apis.henrik.gson.CurrentBundle;
import dev.piste.vayna.apis.henrik.gson.HenrikAccount;
import dev.piste.vayna.apis.henrik.gson.MMR;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class HenrikAPI {

    public static HenrikAccount getAccountByRiotId(String gameName, String tagLine) throws HenrikApiException {
        JsonObject jsonObject = new HttpRequest().doHenrikApiRequest("https://api.henrikdev.xyz/valorant/v1/account/" + URLEncoder.encode(gameName, StandardCharsets.UTF_8) + "/" + URLEncoder.encode(tagLine, StandardCharsets.UTF_8) + "?force=true");
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        if(dataObject == null) throw new HenrikApiException();
        return new Gson().fromJson(dataObject, HenrikAccount.class);
    }

    public static ArrayList<CurrentBundle> getCurrentBundles() {
        JsonObject jsonObject = new HttpRequest().doHenrikApiRequest("https://api.henrikdev.xyz/valorant/v2/store-featured");
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        return new Gson().fromJson(dataArray, new TypeToken<ArrayList<CurrentBundle>>(){}.getType());
    }

    public static MMR getMmr(String puuid, String region) {
        JsonObject jsonObject = new HttpRequest().doHenrikApiRequest("https://api.henrikdev.xyz/valorant/v2/by-puuid/mmr/" + region + "/" + puuid);
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        return new Gson().fromJson(dataObject, MMR.class);
    }



}
