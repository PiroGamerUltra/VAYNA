package dev.piste.vayna.mongodb.match;

import dev.piste.vayna.apis.riot.gson.match.Coach;
import org.bson.Document;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class MongoCoach {

    private static final String PUUID_FIELD = "puuid";
    private static final String TEAM_ID_FIELD = "teamId";

    private final Document document;

    private final String puuid;
    private final String teamId;

    public MongoCoach(Coach coach) {
        this.puuid = coach.getPuuid();
        this.teamId = coach.getTeamId();

        this.document = createDocument();
    }

    public MongoCoach(Document document) {
        this.puuid = document.getString(PUUID_FIELD);
        this.teamId = document.getString(TEAM_ID_FIELD);

        this.document = document;
    }

    public Document toDocument() {
        return document;
    }

    private Document createDocument() {
        return new Document()
                .append(PUUID_FIELD, puuid)
                .append(TEAM_ID_FIELD, teamId);
    }

}