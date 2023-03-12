package dev.piste.vayna.http.models.officer;

import com.google.gson.annotations.SerializedName;
import dev.piste.vayna.http.HttpErrorException;
import dev.piste.vayna.http.apis.OfficerAPI;
import dev.piste.vayna.translations.Language;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Queue {

    @SerializedName("uuid")
    private String id;
    @SerializedName("queueId")
    private String name;
    @SerializedName("displayName")
    private String displayName;
    @SerializedName("description")
    private String description;
    @SerializedName("dropdownText")
    private String dropdownText;
    @SerializedName("selectedText")
    private String tabText;
    @SerializedName("isBeta")
    private boolean isBeta;
    @SerializedName("displayIcon")
    private String displayIcon;
    @SerializedName("assetPath")
    private String assetPath;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getDropdownText() {
        return dropdownText;
    }

    public String getTabText() {
        return tabText;
    }

    public boolean isBeta() {
        return isBeta;
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

    public String getAssetPath() {
        return assetPath;
    }

    public GameMode getParentGameMode(Language language) throws IOException, HttpErrorException, InterruptedException {
        String gameModeId = switch (name.toLowerCase()) {
            case "competitive", "unrated" -> "96bd3920-4f36-d026-2b28-c683eb0bcac5";
            case "deathmatch" -> "a8790ec5-4237-f2f0-e93b-08a8e89865b2";
            case "ggteam" -> "a4ed6518-4741-6dcb-35bd-f884aecdc859";
            case "onefa" -> "4744698a-4513-dc96-9c22-a9aa437e4a58";
            case "snowball" -> "57038d6d-49b1-3a74-c5ef-3395d9f23a97";
            case "spikerush" -> "e921d1e6-416b-c31f-1291-74930c330b7b";
            case "swiftplay" -> "5d0f264b-4ebe-cc63-c147-809e1374484b";
            default -> null;
        };
        if(gameModeId == null) return null;
        return new OfficerAPI().getGameMode(gameModeId, language);
    }

}