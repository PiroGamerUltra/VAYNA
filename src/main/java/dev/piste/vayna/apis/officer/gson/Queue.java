package dev.piste.vayna.apis.officer.gson;

/**
 * @author Piste | https://github.com/zPiste
 */
// GSON CLASS
@SuppressWarnings("ALL")
public class Queue {

    private String uuid;
    private String queueId;
    private String displayName;
    private String description;
    private String dropdownText;
    private String selectedText;
    private boolean isBeta;
    private String displayIcon;
    private String assetPath;

    public String getUuid() {
        return uuid;
    }

    public String getQueueId() {
        return queueId;
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

    public String getSelectedText() {
        return selectedText;
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
}
