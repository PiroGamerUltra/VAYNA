package dev.piste.vayna.http.models.henrik;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class News {

    @SerializedName("banner_url")
    private String banner;
    @SerializedName("category")
    private String category;
    @SerializedName("date")
    private String dateString;
    @SerializedName("external_link")
    private String externalLink;
    @SerializedName("title")
    private String title;
    @SerializedName("url")
    private String uri;

    public String getBanner() {
        return banner;
    }

    public NewsCategory getCategory() {
        return NewsCategory.getById(category.toLowerCase());
    }

    public Date getDate() {
        return getDateFromString(dateString);
    }

    public String getExternalLink() {
        return externalLink;
    }

    public String getTitle() {
        return title;
    }

    public String getURI() {
        return uri;
    }

    private static Date getDateFromString(String dateString) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        return Date.from(localDateTime.atZone(ZoneId.of("CET")).toInstant());
    }

}