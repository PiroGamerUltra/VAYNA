package dev.piste.vayna.apis.officer.gson;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("ALL")
public class Map {

    private String uuid;
    private String displayName;
    private String coordinates;
    private String displayIcon;
    private String listViewIcon;
    private String splash;
    private String mapUrl;

    public String getUuid() {
        return uuid;
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getSplash() {
        return splash;
    }

    public String getListViewIcon() {
        return listViewIcon;
    }

    public String getMapUrl() {
        return mapUrl;
    }
}
