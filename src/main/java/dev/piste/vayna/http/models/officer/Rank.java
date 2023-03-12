package dev.piste.vayna.http.models.officer;

import com.google.gson.annotations.SerializedName;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Rank {

    @SerializedName("tier")
    private int id;
    @SerializedName("tierName")
    private String name;
    @SerializedName("division")
    private String division;
    @SerializedName("divisonName")
    private String divisionName;
    @SerializedName("color")
    private String colorHex;
    @SerializedName("backgroundColor")
    private String backgroundColorHex;
    @SerializedName("smallIcon")
    private String smallIcon;
    @SerializedName("largeIcon")
    private String largeIcon;
    @SerializedName("rankTriangleDownIcon")
    private String triangleDownIcon;
    @SerializedName("rankTriangleUpIcon")
    private String triangleUpIcon;

    public int getId() {
        return id;
    }

    public String getName() {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public String getDivision() {
        return division;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public String getColorHex() {
        return colorHex;
    }

    public String getBackgroundColorHex() {
        return backgroundColorHex;
    }

    public String getSmallIcon() {
        return smallIcon;
    }

    public String getLargeIcon() {
        return largeIcon;
    }

    public String getTriangleDownIcon() {
        return triangleDownIcon;
    }

    public String getTriangleUpIcon() {
        return triangleUpIcon;
    }

}