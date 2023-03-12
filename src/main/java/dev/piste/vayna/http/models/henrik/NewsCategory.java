package dev.piste.vayna.http.models.henrik;

/**
 * @author Piste | https://github.com/PisteDev
 */
public enum NewsCategory {

    DEVELOPMENT("dev"),
    GAME_UPDATES("game_updates"),
    PATCH_NOTES("patch_notes"),
    ESPORTS("esports"),
    ANNOUNCEMENTS("announcements"),
    COMMUNITY("community");

    private final String id;

    NewsCategory(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static NewsCategory getById(String id) {
        for(NewsCategory newsCategory : values()) {
            if(newsCategory.getId().equals(id)) {
                return newsCategory;
            }
        }
        return null;
    }

}