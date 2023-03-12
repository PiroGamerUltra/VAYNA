package dev.piste.vayna.util;

/**
 * @author Piste | https://github.com/PisteDev
 */
public enum RiotRegion {

    EUROPE("eu", "Europe"),
    NORTH_AMERICA("na", "North America"),
    BRAZIL("br", "Brazil"),
    LATIN_AMERICA("latam", "Latin America"),
    KOREA("kr", "Korea"),
    ASIA_PACIFIC("ap", "Asia Pacific");

    private final String id;
    private final String name;

    RiotRegion(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static RiotRegion getRiotRegionById(String id) {
        for(RiotRegion riotRegion : RiotRegion.values()) {
            if(riotRegion.id.equals(id)) {
                return riotRegion;
            }
        }
        return null;
    }

}