package dev.piste.vayna.mongodb;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class AuthKey {

    private static final String DISCORD_USER_ID_FIELD = "discordUserId";
    private static final String AUTH_KEY_FIELD = "authKey";
    private static final MongoCollection<Document> authKeyCollection = Mongo.getAuthKeyCollection();

    private final long discordUserId;
    private final String authKey;

    public AuthKey(long discordUserId) {
        this.discordUserId = discordUserId;
        try (MongoCursor<Document> cursor = authKeyCollection.find(eq(DISCORD_USER_ID_FIELD, discordUserId)).iterator()) {
            if (cursor.hasNext()) {
                Document authKeyDocument = cursor.next();
                authKey = authKeyDocument.getString(AUTH_KEY_FIELD);
            } else {
                authKey = UUID.randomUUID().toString();
                insert();
            }
        } catch (MongoException e) {
            throw new RuntimeException("Error while getting auth key for user " + discordUserId, e);
        }
    }

    public String getAuthKey() {
        return authKey;
    }

    private void insert() {
        Document newAuthKeyDocument = new Document();
        newAuthKeyDocument.put(DISCORD_USER_ID_FIELD, discordUserId);
        newAuthKeyDocument.put(AUTH_KEY_FIELD, authKey);
        authKeyCollection.insertOne(newAuthKeyDocument);
    }

}
