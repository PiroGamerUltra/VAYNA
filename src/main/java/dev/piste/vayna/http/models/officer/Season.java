package dev.piste.vayna.http.models.officer;

import com.google.gson.annotations.SerializedName;
import dev.piste.vayna.http.HttpErrorException;
import dev.piste.vayna.http.apis.OfficerAPI;
import dev.piste.vayna.translations.Language;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Season {

    @SerializedName("uuid")
    private String id;
    @SerializedName("displayName")
    private String displayName;
    @SerializedName("type")
    private String type;
    @SerializedName("startTime")
    private String startTime;
    @SerializedName("endTime")
    private String endTime;
    @SerializedName("parentUuid")
    private String parentId;
    @SerializedName("assetPath")
    private String assetPath;

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getType() {
        return type;
    }

    public Date getStartDate() {
        return getDateFromString(startTime);
    }

    public Date getEndDate() {
        return getDateFromString(endTime);
    }

    public Season getParentSeason(Language language) throws IOException, HttpErrorException, InterruptedException {
        if(parentId == null) return null;
        return new OfficerAPI().getSeason(parentId, language);
    }

    public String getAssetPath() {
        return assetPath;
    }

    private static Date getDateFromString(String dateString) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        return Date.from(localDateTime.atZone(ZoneId.of("CET")).toInstant());
    }

}