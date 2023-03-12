package dev.piste.vayna.http.models.officer;

import com.google.gson.annotations.SerializedName;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Theme {

    @SerializedName("uuid")
    private String id;
    @SerializedName("displayName")
    private String displayName;
    @SerializedName("displayIcon")
    private String displayIcon;
    @SerializedName("storeFeaturedImage")
    private String storeFeaturedImage;
    @SerializedName("assetPath")
    private String assetPath;

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

    public String getStoreFeaturedImage() {
        return storeFeaturedImage;
    }

    public String getAssetPath() {
        return assetPath;
    }

}