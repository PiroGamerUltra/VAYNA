package dev.piste.vayna.http.models.officer;

import com.google.gson.annotations.SerializedName;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class ShopData {

    @SerializedName("cost")
    private int price;
    @SerializedName("category")
    private String category;
    @SerializedName("categoryText")
    private String categoryText;
    @SerializedName("gridPosition")
    private GridPosition gridPosition;
    @SerializedName("canBeTrashed")
    private boolean isTrashable;
    @SerializedName("image")
    private String image;
    @SerializedName("newImage")
    private String newImage;
    @SerializedName("newImage2")
    private String newImage2;
    @SerializedName("assetPath")
    private String assetPath;

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public GridPosition getGridPosition() {
        return gridPosition;
    }

    public boolean isTrashable() {
        return isTrashable;
    }

    public String getImage() {
        return image;
    }

    public String getNewImage() {
        return newImage;
    }

    public String getNewImage2() {
        return newImage2;
    }

    public String getAssetPath() {
        return assetPath;
    }

    public static class GridPosition {

        @SerializedName("row")
        private int row;
        @SerializedName("column")
        private int column;

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

    }

}