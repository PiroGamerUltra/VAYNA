package dev.piste.vayna.apis.valorantapi.gson;

// GSON CLASS
@SuppressWarnings("ALL")
public class Map {

    private String displayName;
    private String coordinates;
    private String displayIcon;
    private String listViewIcon;
    private String splash;

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
}
