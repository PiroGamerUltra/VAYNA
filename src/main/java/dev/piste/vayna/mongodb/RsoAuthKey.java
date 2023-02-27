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

    private static final String DISCORD_USER_ID_FIELD = "discordUserId";
    private static final String RSO_AUTH_KEY_FIELD = "rsoAuthKey";
    private static final String EXPIRATION_DATE_FIELD = "expirationDate";
    private static final MongoCollection<Document> collection = Mongo.getRsoAuthKeysCollection();

    private final long discordUserId;
    private final String rsoAuthKey;
    private Date expirationDate;

    public RsoAuthKey(long discordUserId) {
        this.discordUserId = discordUserId;
        try (MongoCursor<Document> cursor = collection.find(eq(DISCORD_USER_ID_FIELD, discordUserId)).iterator()) {
            if (cursor.hasNext()) {
                Document document = cursor.next();
                if (document.getDate(EXPIRATION_DATE_FIELD).before(Date.from(Instant.now()))) {
                    collection.deleteOne(eq(DISCORD_USER_ID_FIELD, discordUserId));
                    rsoAuthKey = UUID.randomUUID().toString();
                    insert();
                    RsoAuthKey newRsoAuthKey = new RsoAuthKey(discordUserId);
                    expirationDate = newRsoAuthKey.getExpirationDate();
                } else {
                    rsoAuthKey = document.getString(RSO_AUTH_KEY_FIELD);
                    expirationDate = document.getDate(EXPIRATION_DATE_FIELD);
                }
            } else {
                rsoAuthKey = UUID.randomUUID().toString();
                insert();
                RsoAuthKey newRsoAuthKey = new RsoAuthKey(discordUserId);
                expirationDate = newRsoAuthKey.getExpirationDate();
            }
        } catch (MongoException e) {
            throw new RuntimeException("Error while getting auth key for user " + discordUserId, e);
        }
    }

    public String getRsoAuthKey() {
        return rsoAuthKey;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void refreshExpirationDate() {
        this.expirationDate = Date.from(Instant.now().plus(Duration.ofHours(1)));
        collection.updateOne(eq(DISCORD_USER_ID_FIELD, discordUserId), Updates.set(EXPIRATION_DATE_FIELD, expirationDate));
    }

    private void insert() {
        Document newDocument = new Document();
        newDocument.put(DISCORD_USER_ID_FIELD, discordUserId);
        newDocument.put(RSO_AUTH_KEY_FIELD, rsoAuthKey);
        newDocument.put(EXPIRATION_DATE_FIELD, Date.from(Instant.now().plus(Duration.ofHours(1))));
        collection.insertOne(newDocument);
    }

}
