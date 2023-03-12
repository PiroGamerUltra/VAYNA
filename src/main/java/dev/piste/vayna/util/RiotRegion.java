package dev.piste.vayna.util;

import dev.piste.vayna.translations.Language;

/**
 * @author Piste | https://github.com/PisteDev
 */
public enum RiotRegion {

    EUROPE("eu"),
    NORTH_AMERICA("na"),
    BRAZIL("br"),
    LATIN_AMERICA("latam"),
    KOREA("kr"),
    ASIA_PACIFIC("ap");

    private final String id;

    RiotRegion(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName(Language language) {
        return language.getTranslation(String.format("region-%s-name", id));
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