package dev.piste.vayna.http.models.officer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Spray {

    @SerializedName("uuid")
    private String id;
    @SerializedName("displayName")
    private String displayName;
    @SerializedName("category")
    private String category;
    @SerializedName("themeUuid")
    private String themeId;
    @SerializedName("displayIcon")
    private String displayIcon;
    @SerializedName("fullIcon")
    private String fullIcon;
    @SerializedName("fullTransparentIcon")
    private String fullTransparentIcon;
    @SerializedName("animationPng")
    private String animationPng;
    @SerializedName("animationGif")
    private String animationGif;
    @SerializedName("assetPath")
    private String assetPath;
    @SerializedName("levels")
    private List<Level> levels;

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCategory() {
        return category;
    }

    public String getThemeId() {
        return themeId;
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

    public String getFullIcon() {
        return fullIcon;
    }

    public String getFullTransparentIcon() {
        return fullTransparentIcon;
    }

    public String getAnimationPng() {
        return animationPng;
    }

    public String getAnimationGif() {
        return animationGif;
    }

    public String getAssetPath() {
        return assetPath;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public static class Level {

        @SerializedName("uuid")
        private String id;
        @SerializedName("sprayLevel")
        private int level;
        @SerializedName("displayName")
        private String displayName;
        @SerializedName("displayIcon")
        private String displayIcon;
        @SerializedName("assetPath")
        private String assetPath;

        public String getId() {
            return id;
        }

        public int getLevel() {
            return level;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getDisplayIcon() {
            return displayIcon;
        }

        public String getAssetPath() {
            return assetPath;
        }

    }

}