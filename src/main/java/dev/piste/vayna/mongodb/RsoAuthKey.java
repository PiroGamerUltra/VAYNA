package dev.piste.vayna.mongodb;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class RsoAuthKey {

    private static final String DISCORD_USER_ID_FIELD = "_id";
    private static final String AUTH_KEY_FIELD = "authKey";
    private static final String EXPIRATION_DATE_FIELD = "expirationDate";
    private static final MongoCollection<Document> COLLECTION = Mongo.getRsoAuthKeysCollection();

    private final long discordUserId;
    private final String authKey;
    private Date expirationDate;

    public RsoAuthKey(long discordUserId) {
        this.discordUserId = discordUserId;
        try (MongoCursor<Document> cursor = COLLECTION.find(eq(DISCORD_USER_ID_FIELD, discordUserId)).iterator()) {
            if (cursor.hasNext()) {
                Document document = cursor.next();
                if (document.getDate(EXPIRATION_DATE_FIELD).before(Date.from(Instant.now()))) {
                    COLLECTION.deleteOne(eq(DISCORD_USER_ID_FIELD, discordUserId));
                    this.authKey = UUID.randomUUID().toString();
                    this.expirationDate = Date.from(Instant.now().plus(Duration.ofHours(1)));
                    insertDocument();
                } else {
                    this.authKey = document.getString(AUTH_KEY_FIELD);
                    this.expirationDate = document.getDate(EXPIRATION_DATE_FIELD);
                }
            } else {
                this.authKey = UUID.randomUUID().toString();
                this.expirationDate = Date.from(Instant.now().plus(Duration.ofHours(1)));
                insertDocument();
            }
        } catch (MongoException e) {
            throw new RuntimeException("Error while getting auth key for user " + discordUserId, e);
        }
    }

    public String getAuthKey() {
        return authKey;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void refreshExpirationDate() {
        this.expirationDate = Date.from(Instant.now().plus(Duration.ofHours(1)));
        COLLECTION.updateOne(eq(DISCORD_USER_ID_FIELD, discordUserId), Updates.set(EXPIRATION_DATE_FIELD, expirationDate));
    }

    private void insertDocument() {
        Document newDocument = new Document()
                .append(DISCORD_USER_ID_FIELD, discordUserId)
                .append(AUTH_KEY_FIELD, authKey)
                .append(EXPIRATION_DATE_FIELD, expirationDate);
        COLLECTION.insertOne(newDocument);
    }

}
