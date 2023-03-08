package dev.piste.vayna.apis.entities.officer;

import com.google.gson.annotations.SerializedName;
import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.OfficerAPI;

import java.io.IOException;
import java.util.List;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Skin {

    @SerializedName("uuid")
    private String id;
    @SerializedName("displayName")
    private String displayName;
    @SerializedName("themeUuid")
    private String themeId;
    @SerializedName("contentTierUuid")
    private String contentTierId;
    @SerializedName("displayIcon")
    private String displayIcon;
    @SerializedName("wallpaper")
    private String wallpaper;
    @SerializedName("assetPath")
    private String assetPath;
    @SerializedName("chromas")
    private List<Chroma> chromas;
    @SerializedName("levels")
    private List<Level> levels;

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Theme getTheme(String languageCode) throws IOException, HttpErrorException, InterruptedException {
        return new OfficerAPI().getTheme(themeId.toLowerCase(), languageCode);
    }

    public String getContentTierId() {
        return contentTierId;
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

    public String getWallpaper() {
        return wallpaper;
    }

    public String getAssetPath() {
        return assetPath;
    }

    public List<Chroma> getChromas() {
        return chromas;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public static class Chroma {

        @SerializedName("uuid")
        private String id;
        @SerializedName("displayName")
        private String displayName;
        @SerializedName("displayIcon")
        private String displayIcon;
        @SerializedName("fullRender")
        private String fullRender;
        @SerializedName("swatch")
        private String swatch;
        @SerializedName("streamedVideo")
        private String video;
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

        public String getFullRender() {
            return fullRender;
        }

        public String getSwatch() {
            return swatch;
        }

        public String getVideo() {
            return video;
        }

        public String getAssetPath() {
            return assetPath;
        }

    }

    public static class Level {

        @SerializedName("uuid")
        private String id;
        @SerializedName("displayName")
        private String displayName;
        @SerializedName("levelItem")
        private String levelItem;
        @SerializedName("displayIcon")
        private String displayIcon;
        @SerializedName("streamedVideo")
        private String video;
        @SerializedName("assetPath")
        private String assetPath;

        public String getId() {
            return id;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getLevelItem() {
            return levelItem;
        }

        public String getDisplayIcon() {
            return displayIcon;
        }

        public String getVideo() {
            return video;
        }

        public String getAssetPath() {
            return assetPath;
        }

    }

}