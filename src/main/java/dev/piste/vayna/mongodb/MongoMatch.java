package dev.piste.vayna.mongodb;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.riot.gson.Match;
import dev.piste.vayna.apis.riot.gson.match.Coach;
import dev.piste.vayna.apis.riot.gson.match.Player;
import dev.piste.vayna.apis.riot.gson.match.Team;
import dev.piste.vayna.mongodb.match.MongoCoach;
import dev.piste.vayna.mongodb.match.MongoMatchInfo;
import dev.piste.vayna.mongodb.match.MongoPlayer;
import dev.piste.vayna.mongodb.match.MongoTeam;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class MongoMatch {

    private static final MongoCollection<Document> COLLECTION = Mongo.getMatchesCollection();
    private static final String MATCH_ID_FIELD = "_id";
    private static final String MATCH_INFO_FIELD = "matchInfo";
    private static final String PLAYERS_FIELD = "players";
    private static final String COACHES_FIELD = "coaches";
    private static final String TEAMS_FIELD = "teams";

    private final String matchId;
    private final MongoMatchInfo matchInfo;
    private final List<MongoPlayer> players = new ArrayList<>();
    private final List<MongoCoach> coaches = new ArrayList<>();
    private final List<MongoTeam> teams = new ArrayList<>();

    private final boolean isExisting;

    public MongoMatch(Match match) throws IOException, HttpErrorException, InterruptedException {
        this.matchId = match.getMatchInfo().getMatchId();
        this.matchInfo = new MongoMatchInfo(match.getMatchInfo());
        for(Player player : match.getPlayers()) {
            this.players.add(new MongoPlayer(player));
        }
        for(Coach coach : match.getCoaches()) {
            this.coaches.add(new MongoCoach(coach));
        }
        for(Team team : match.getTeams()) {
            this.teams.add(new MongoTeam(team));
        }
        try (MongoCursor<Document> cursor = COLLECTION.find(eq(MATCH_ID_FIELD, matchId)).iterator()) {
            isExisting = cursor.hasNext();
        } catch (MongoException e) {
            throw new RuntimeException("Error while getting match " + matchId, e);
        }
    }

    public MongoMatch(String matchId) {
        try (MongoCursor<Document> cursor = COLLECTION.find(eq(MATCH_ID_FIELD, matchId)).iterator()) {
            if(cursor.hasNext()) {
                Document matchDocument = cursor.next();
                this.matchId = matchDocument.getString(MATCH_ID_FIELD);
                this.matchInfo = new MongoMatchInfo(matchDocument.get(MATCH_INFO_FIELD, Document.class));
                for(Document playerDocument : matchDocument.getList(PLAYERS_FIELD, Document.class)) {
                    this.players.add(new MongoPlayer(playerDocument));
                }
                for(Document coachDocument : matchDocument.getList(COACHES_FIELD, Document.class)) {
                    this.coaches.add(new MongoCoach(coachDocument));
                }
                for(Document teamDocument : matchDocument.getList(TEAMS_FIELD, Document.class)) {
                    this.teams.add(new MongoTeam(teamDocument));
                }
                isExisting = true;
            } else {
                this.matchId = null;
                this.matchInfo = null;
                isExisting = false;
            }
        } catch (MongoException e) {
            throw new RuntimeException("Error while getting match " + matchId, e);
        }
    }

    public String getMatchId() {
        return matchId;
    }

    public MongoMatchInfo getMatchInfo() {
        return matchInfo;
    }

    public boolean isExisting() {
        return isExisting;
    }

    public void insert() {
        Document newDocument = new Document()
                .append(MATCH_ID_FIELD, matchId)
                .append(MATCH_INFO_FIELD, matchInfo.toDocument())
                .append(PLAYERS_FIELD, players.stream().map(MongoPlayer::toDocument).toList())
                .append(COACHES_FIELD, coaches.stream().map(MongoCoach::toDocument).toList())
                .append(TEAMS_FIELD, teams.stream().map(MongoTeam::toDocument).toList());
        COLLECTION.insertOne(newDocument);
    }

}