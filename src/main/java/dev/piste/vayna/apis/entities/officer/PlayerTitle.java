package dev.piste.vayna.apis.entities.officer;

import com.google.gson.annotations.SerializedName;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class PlayerTitle {

    @SerializedName("uuid")
    private String id;
    @SerializedName("displayName")
    private String displayName;
    @SerializedName("titleText")
    private String titleText;
    @SerializedName("isHiddenIfNotOwned")
    private boolean isHiddenIfNotOwned;
    @SerializedName("assetPath")
    private String assetPath;

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getTitleText() {
        return titleText;
    }

    public boolean isHiddenIfNotOwned() {
        return isHiddenIfNotOwned;
    }

    public String getAssetPath() {
        return assetPath;
    }

}