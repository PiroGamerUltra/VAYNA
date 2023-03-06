package dev.piste.vayna.mongodb.match;

import dev.piste.vayna.apis.riot.gson.match.Team;
import org.bson.Document;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class MongoTeam {

    private static final String TEAM_ID_FIELD = "teamId";
    private static final String HAS_WON_FIELD = "hasWon";
    private static final String ROUNDS_PLAYED_FIELD = "roundsPlayed";
    private static final String ROUNDS_WON_FIELD = "roundsWon";

    private final Document document;

    private final String teamId;
    private final boolean hasWon;
    private final int roundsPlayed;
    private final int roundsWon;

    public MongoTeam(Team team) {
        this.teamId = team.getTeamId();
        this.hasWon = team.hasWon();
        this.roundsPlayed = team.getRoundsPlayed();
        this.roundsWon = team.getRoundsWon();

        this.document = createDocument();
    }

    public MongoTeam(Document document) {
        this.teamId = document.getString(TEAM_ID_FIELD);
        this.hasWon = document.getBoolean(HAS_WON_FIELD);
        this.roundsPlayed = document.getInteger(ROUNDS_PLAYED_FIELD);
        this.roundsWon = document.getInteger(ROUNDS_WON_FIELD);

        this.document = document;
    }

    public Document toDocument() {
        return document;
    }

    private Document createDocument() {
        return new Document()
                .append(TEAM_ID_FIELD, teamId)
                .append(HAS_WON_FIELD, hasWon)
                .append(ROUNDS_PLAYED_FIELD, roundsPlayed)
                .append(ROUNDS_WON_FIELD, roundsWon);
    }

}