package dev.piste.vayna.apis.officer.gson.competitivetier;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Tier {

    private int tier;
    private String tierName;
    private String color;
    private String smallIcon;
    private String largeIcon;

    public int getTier() {
        return tier;
    }

    public String getTierName() {
        return tierName.substring(0,1).toUpperCase() + tierName.substring(1).toLowerCase();
    }

    public String getColor() {
        return color;
    }

    public String getSmallIcon() {
        return smallIcon;
    }

    public String getLargeIcon() {
        return largeIcon;
    }
}
