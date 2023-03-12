package dev.piste.vayna.http.models.riotgames;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class PlatformData {

    @SerializedName("id")
    private String region;
    @SerializedName("name")
    private String regionName;
    @SerializedName("locales")
    private List<String> locales;
    @SerializedName("maintenances")
    private List<Status> maintenances;
    @SerializedName("incidents")
    private List<Status> incidents;

    public String getRegion() {
        return region;
    }

    public String getRegionName() {
        return regionName;
    }

    public List<String> getLocales() {
        return locales;
    }

    public List<Status> getMaintenances() {
        return maintenances;
    }

    public List<Status> getIncidents() {
        return incidents;
    }

    public static class Status {
        @SerializedName("id")
        private int id;
        @SerializedName("maintenance_status")
        private String status;
        @SerializedName("incident_severity")
        private String severity;
        @SerializedName("titles")
        private List<Translation> titles;
        @SerializedName("updates")
        private List<Update> updates;
        @SerializedName("created_at")
        private String creationDate;
        @SerializedName("archive_at")
        private String archivationDate;
        @SerializedName("updated_at")
        private String updationDate;
        @SerializedName("platforms")
        private List<String> platforms;

        public int getId() {
            return id;
        }

        public String getStatus() {
            return status;
        }

        public String getSeverity() {
            return severity;
        }

        public List<Translation> getTitles() {
            return titles;
        }

        public List<Update> getUpdates() {
            return updates;
        }

        public Date getCreationDate() {
            return getDateFromString(creationDate);
        }

        public Date getArchivationDate() {
            return getDateFromString(archivationDate);
        }

        public Date getUpdationDate() {
            return getDateFromString(updationDate);
        }

        public List<String> getPlatforms() {
            return platforms;
        }

    }

    public static class Update {
        @SerializedName("id")
        private int id;
        @SerializedName("author")
        private String author;
        @SerializedName("publish")
        private boolean published;
        @SerializedName("publish_locations")
        private List<String> publishLocations;
        @SerializedName("translations")
        private List<Translation> translations;
        @SerializedName("created_at")
        private String creationDate;
        @SerializedName("updated_at")
        private String updationDate;

        public int getId() {
            return id;
        }

        public String getAuthor() {
            return author;
        }

        public boolean isPublished() {
            return published;
        }

        public List<String> getPublishLocations() {
            return publishLocations;
        }

        public List<Translation> getTranslations() {
            return translations;
        }

        public Date getCreationDate() {
            return getDateFromString(creationDate);
        }

        public Date getUpdationDate() {
            return getDateFromString(updationDate);
        }
    }

    public static class Translation {
        @SerializedName("locale")
        private String locale;
        @SerializedName("content")
        private String content;

        public String getLocale() {
            return locale;
        }

        public String getContent() {
            return content;
        }
    }

    private static Date getDateFromString(String dateString) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"));
        return Date.from(localDateTime.atZone(ZoneId.of("CET")).toInstant());
    }

}