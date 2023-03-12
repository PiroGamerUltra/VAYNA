package dev.piste.vayna.http.models.henrik;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class StoreBundle {

    @SerializedName("bundle_uuid")
    private String id;
    @SerializedName("bundle_price")
    private int price;
    @SerializedName("whole_sale_only")
    private boolean isWholeSaleOnly;
    @SerializedName("items")
    private List<Item> items;
    @SerializedName("seconds_remaining")
    private long remainingSeconds;

    public String getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public boolean isWholeSaleOnly() {
        return isWholeSaleOnly;
    }

    public List<Item> getItems() {
        return items;
    }

    public Date getRemovalDate() {
        return new Date(System.currentTimeMillis() + (remainingSeconds * 1000));
    }

    public static class Item {

        @SerializedName("uuid")
        private String id;
        @SerializedName("name")
        private String displayName;
        @SerializedName("image")
        private String displayIcon;
        @SerializedName("type")
        private String type;
        @SerializedName("amount")
        private int amount;
        @SerializedName("discount_percent")
        private int discountPercent;
        @SerializedName("base_price")
        private int basePrice;
        @SerializedName("discounted_price")
        private int discountedPrice;
        @SerializedName("promo_item")
        private boolean isPromotional;

        public String getId() {
            return id;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getDisplayIcon() {
            return displayIcon;
        }

        public String getType() {
            return type;
        }

        public int getAmount() {
            return amount;
        }

        public int getDiscountPercent() {
            return discountPercent;
        }

        public int getBasePrice() {
            return basePrice;
        }

        public int getDiscountedPrice() {
            return discountedPrice;
        }

        public boolean isPromotional() {
            return isPromotional;
        }

    }

}