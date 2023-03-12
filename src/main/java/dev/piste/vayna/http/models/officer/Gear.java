package dev.piste.vayna.http.models.officer;

import com.google.gson.annotations.SerializedName;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Gear {

    @SerializedName("uuid")
    private String id;
    @SerializedName("displayName")
    private String displayName;
    @SerializedName("description")
    private String description;
    @SerializedName("displayIcon")
    private String displayIcon;
    @SerializedName("assetPath")
    private String assetPath;
    @SerializedName("ShopData")
    private ShopData shopData;

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

    public String getAssetPath() {
        return assetPath;
    }

    public ShopData getShopData() {
        return shopData;
    }

}