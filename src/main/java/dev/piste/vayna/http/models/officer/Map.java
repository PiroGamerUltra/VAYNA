package dev.piste.vayna.http.models.officer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Map {

    @SerializedName("uuid")
    private String id;
    @SerializedName("displayName")
    private String displayName;
    @SerializedName("coordinates")
    private String coordinates;
    @SerializedName("displayIcon")
    private String displayIcon;
    @SerializedName("listViewIcon")
    private String listViewIcon;
    @SerializedName("splash")
    private String splash;
    @SerializedName("assetPath")
    private String assetPath;
    @SerializedName("mapUrl")
    private String path;
    @SerializedName("xMultiplier")
    private double xMultiplier;
    @SerializedName("yMultiplier")
    private double yMultiplier;
    @SerializedName("xScalarToAdd")
    private double xScalarToAdd;
    @SerializedName("yScalarToAdd")
    private double yScalarToAdd;
    @SerializedName("callouts")
    private List<Region> regions;

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

    public String getListViewIcon() {
        return listViewIcon;
    }

    public String getSplash() {
        return splash;
    }

    public String getAssetPath() {
        return assetPath;
    }

    public String getPath() {
        return path;
    }

    public double getxMultiplier() {
        return xMultiplier;
    }

    public double getyMultiplier() {
        return yMultiplier;
    }

    public double getxScalarToAdd() {
        return xScalarToAdd;
    }

    public double getyScalarToAdd() {
        return yScalarToAdd;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public static class Region {

        @SerializedName("regionName")
        private String name;
        @SerializedName("superRegionName")
        private String superRegionName;
        @SerializedName("location")
        private Location location;

        public String getName() {
            return name;
        }

        public String getSuperRegionName() {
            return superRegionName;
        }

        public Location getLocation() {
            return location;
        }

        public static class Location {

            @SerializedName("x")
            private double x;
            @SerializedName("y")
            private double y;

            public double getX() {
                return x;
            }

            public double getY() {
                return y;
            }

        }

    }

}