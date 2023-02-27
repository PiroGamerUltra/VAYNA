package dev.piste.vayna.apis.riot.gson.match.roundresult;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class PlayerLocation {

    private String puuid;
    private float viewRadians;
    private Location location;

    public String getPuuid() {
        return puuid;
    }

    public float getViewRadians() {
        return viewRadians;
    }

    public Location getLocation() {
        return location;
    }
}
