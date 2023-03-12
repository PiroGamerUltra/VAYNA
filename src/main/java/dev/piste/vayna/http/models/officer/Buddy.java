package dev.piste.vayna.http.models.officer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Buddy {

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

    public boolean isHiddenIfNotOwned() {
        return isHiddenIfNotOwned;
    }

    public String getThemeId() {
        return themeId;
    }

    public String getDisplayIcon() {
        return displayIcon;
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
        @SerializedName("level")
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