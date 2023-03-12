package dev.piste.vayna.http.models.officer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Agent {

    @SerializedName("uuid")
    private String id;
    @SerializedName("displayName")
    private String displayName;
    @SerializedName("description")
    private String description;
    @SerializedName("developerName")
    private String developerName;
    @SerializedName("characterTags")
    private String[] tags;
    @SerializedName("displayIcon")
    private String displayIcon;
    @SerializedName("displayIconSmall")
    private String displayIconSmall;
    @SerializedName("bustPortrait")
    private String bustPortrait;
    @SerializedName("fullPortrait")
    private String fullPortrait;
    @SerializedName("killfeedPortait")
    private String killfeedPortait;
    @SerializedName("background")
    private String background;
    @SerializedName("backGroundGradientColors")
    private String[] backGroundGradientColors;
    @SerializedName("assetPath")
    private String assetPath;
    @SerializedName("isFullPortraitRightFacing")
    private boolean isFullPortraitRightFacing;
    @SerializedName("isAvailableForTest")
    private boolean isAvailableForTest;
    @SerializedName("isBaseContent")
    private boolean isBaseContent;
    @SerializedName("role")
    private Role role;
    @SerializedName("abilities")
    private List<Ability> abilities;
    @SerializedName("voiceLine")
    private VoiceLine voiceLine;

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public String[] getTags() {
        return tags;
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

    public String getDisplayIconSmall() {
        return displayIconSmall;
    }

    public String getBustPortrait() {
        return bustPortrait;
    }

    public String getFullPortrait() {
        return fullPortrait;
    }

    public String getKillfeedPortait() {
        return killfeedPortait;
    }

    public String getBackground() {
        return background;
    }

    public String[] getBackGroundGradientColors() {
        return backGroundGradientColors;
    }

    public String getAssetPath() {
        return assetPath;
    }

    public boolean isFullPortraitRightFacing() {
        return isFullPortraitRightFacing;
    }

    public boolean isAvailableForTest() {
        return isAvailableForTest;
    }

    public boolean isBaseContent() {
        return isBaseContent;
    }

    public Role getRole() {
        return role;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public VoiceLine getVoiceLine() {
        return voiceLine;
    }

    public static class Role {

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

    }

    public static class Ability {

        @SerializedName("slot")
        private String slotName;
        @SerializedName("displayName")
        private String displayName;
        @SerializedName("description")
        private String description;
        @SerializedName("displayIcon")
        private String displayIcon;

        public String getSlotKey() {
            return switch (slotName.toLowerCase()) {
                case "ability1" -> "Q";
                case "ability2" -> "E";
                case "grenade" -> "C";
                case "ultimate" -> "X";
                default -> slotName;
            };
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getDisplayIcon() {
            return displayIcon;
        }

        public String getDescription() {
            return description;
        }

    }

    public static class VoiceLine {

        @SerializedName("minDuration")
        private float minDuration;
        @SerializedName("maxDuration")
        private float maxDuration;
        @SerializedName("mediaList")
        private List<Media> mediaList;

        public float getMinDuration() {
            return minDuration;
        }

        public float getMaxDuration() {
            return maxDuration;
        }

        public List<Media> getMediaList() {
            return mediaList;
        }

        public static class Media {

            @SerializedName("id")
            private int id;
            @SerializedName("wwise")
            private String wwise;
            @SerializedName("wave")
            private String wave;

            public int getId() {
                return id;
            }

            public String getWwise() {
                return wwise;
            }

            public String getWave() {
                return wave;
            }

        }

    }

}