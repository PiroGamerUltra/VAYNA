package dev.piste.vayna.http.models.officer;

import com.google.gson.annotations.SerializedName;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class GameMode {

    @SerializedName("uuid")
    private String id;
    @SerializedName("displayName")
    private String displayName;
    @SerializedName("duration")
    private String duration;
    @SerializedName("allowsMatchTimeouts")
    private boolean isAllowingMatchTimeouts;
    @SerializedName("isTeamVoiceAllowed")
    private boolean isTeamVoiceAllowed;
    @SerializedName("isMinimapHidden")
    private boolean isMinimapHidden;
    @SerializedName("orbCount")
    private int orbCount;
    @SerializedName("roundsPerHalf")
    private int roundsPerHalfCount;
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

    public String getDuration() {
        return duration;
    }

    public boolean isAllowingMatchTimeouts() {
        return isAllowingMatchTimeouts;
    }

    public boolean isTeamVoiceAllowed() {
        return isTeamVoiceAllowed;
    }

    public boolean isMinimapHidden() {
        return isMinimapHidden;
    }

    public int getOrbCount() {
        return orbCount;
    }

    public int getRoundsPerHalfCount() {
        return roundsPerHalfCount;
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

    public String getAssetPath() {
        return assetPath;
    }

}