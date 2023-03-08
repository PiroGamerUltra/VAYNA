package dev.piste.vayna.mongodb;

import com.google.gson.Gson;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import dev.piste.vayna.apis.entities.riotgames.Match;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class MongoMatch {

    private static final MongoCollection<Document> COLLECTION = Mongo.getMatchesCollection();

    private final Match match;
    private final boolean isExisting;

    public MongoMatch(Match match) {
        this.match = match;
        isExisting = false;
    }

    public MongoMatch(String matchId) {
        try (MongoCursor<Document> cursor = COLLECTION.find(eq("_id", matchId)).iterator()){
            if (cursor.hasNext()) {
                this.match = new Gson().fromJson(cursor.next().toJson(), Match.class);
                isExisting = true;
            } else {
                this.match = null;
                isExisting = false;
            }
        } catch (MongoException e) {
            throw new RuntimeException("Error while getting match with id " + matchId, e);
        }
    }

    public boolean isExisting() {
        return isExisting;
    }

    public Match getMatch() {
        return match;
    }

    public MongoMatch insert() {
        if (isExisting) {
            throw new RuntimeException("Match with id " + match.getMatchInfo().getMatchId() + " already exists");
        }
        COLLECTION.insertOne(Document.parse(new Gson().toJson(match.setMongoId())));
        return this;
    }
}