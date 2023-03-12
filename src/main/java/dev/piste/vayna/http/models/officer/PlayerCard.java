package dev.piste.vayna.http.models.officer;

import com.google.gson.annotations.SerializedName;
import dev.piste.vayna.http.HttpErrorException;
import dev.piste.vayna.http.apis.OfficerAPI;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class PlayerCard {

    @SerializedName("uuid")
    private String id;
    @SerializedName("displayName")
    private String displayName;
    @SerializedName("isHiddenIfNotOwned")
    private boolean isHiddenIfNotOwned;
    @SerializedName("themeUuid")
    private String themeId;
    @SerializedName("displayIcon")
    private String displayIcon;
    @SerializedName("smallArt")
    private String smallArt;
    @SerializedName("wideArt")
    private String wideArt;
    @SerializedName("largeArt")
    private String largeArt;
    @SerializedName("assetPath")
    private String assetPath;

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isHiddenIfNotOwned() {
        return isHiddenIfNotOwned;
    }

    public Theme getTheme(String languageCode) throws IOException, HttpErrorException, InterruptedException {
        return new OfficerAPI().getTheme(themeId, languageCode);
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

    public String getSmallArt() {
        return smallArt;
    }

    public String getWideArt() {
        return wideArt;
    }

    public String getLargeArt() {
        return largeArt;
    }

    public String getAssetPath() {
        return assetPath;
    }

}