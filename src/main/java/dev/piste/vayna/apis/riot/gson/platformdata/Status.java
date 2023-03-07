package dev.piste.vayna.apis.riot.gson.platformdata;

import java.util.List;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Status {

    private int id;
    private String maintenance_status;
    private String incident_severity;
    private List<Content> titles;
    private List<Update> updates;
    private String created_at;
    private String archive_at;
    private String updated_at;
    private List<String> platforms;

    public int getId() {
        return id;
    }

    public String getMaintenanceStatus() {
        return maintenance_status;
    }

    public String getIncidentSeverity() {
        return incident_severity;
    }

    public List<Content> getTitles() {
        return titles;
    }

    public List<Update> getUpdates() {
        return updates;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public String getArchiveAt() {
        return archive_at;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public static class Content {
        private String locale;
        private String content;

        public String getLocale() {
            return locale;
        }

        public String getContent() {
            return content;
        }
    }

    public static class Update {
        private int id;
        private String author;
        private boolean publish;
        private List<String> publish_locations;
        private List<Content> translations;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public String getAuthor() {
            return author;
        }

        public boolean isPublish() {
            return publish;
        }

        public List<String> getPublishLocations() {
            return publish_locations;
        }

        public List<Content> getTranslations() {
            return translations;
        }

        public String getCreatedAt() {
            return created_at;
        }

        public String getUpdatedAt() {
            return updated_at;
        }
    }

}