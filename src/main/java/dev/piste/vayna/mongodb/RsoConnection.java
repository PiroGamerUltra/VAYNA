package dev.piste.vayna.mongodb;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Date;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class RsoConnection {

    private static final String DISCORD_USER_ID_FIELD = "discordUserId";
    private static final String RIOT_PUUID_FIELD = "riotPuuid";
    private static final String PUBLICLY_VISIBLE_FIELD = "publiclyVisible";
    private static final String CREATION_DATE_FIELD = "creationDate";
    private static final MongoCollection<Document> collection = Mongo.getRsoConnectionsCollection();

    private final Document document;
    private final boolean isExisting;
    private final long discordUserId;
    private final String riotPuuid;
    private boolean publiclyVisible;
    private final Date creationDate;


    public RsoConnection(long discordUserId) {
        this.discordUserId = discordUserId;
        try (MongoCursor<Document> cursor = collection.find(eq(DISCORD_USER_ID_FIELD, discordUserId)).iterator()) {
            if (cursor.hasNext()) {
                document = cursor.next();
                riotPuuid = document.getString(RIOT_PUUID_FIELD);
                publiclyVisible = document.getBoolean(PUBLICLY_VISIBLE_FIELD);
                creationDate = document.getDate(CREATION_DATE_FIELD);
                isExisting = true;
            } else {
                document = null;
                riotPuuid = null;
                publiclyVisible = false;
                creationDate = null;
                isExisting = false;
            }
        } catch (MongoException e) {
            throw new RuntimeException("Error while getting linked account for user " + discordUserId, e);
        }
    }

    public RsoConnection(String riotPuuid) {
        this.riotPuuid = riotPuuid;
        try (MongoCursor<Document> cursor = collection.find(eq(RIOT_PUUID_FIELD, riotPuuid)).iterator()) {
            if (cursor.hasNext()) {
                document = cursor.next();
                discordUserId = document.getLong(DISCORD_USER_ID_FIELD);
                publiclyVisible = document.getBoolean(PUBLICLY_VISIBLE_FIELD);
                creationDate = document.getDate(CREATION_DATE_FIELD);
                isExisting = true;
            } else {
                document = null;
                discordUserId = 0L;
                publiclyVisible = false;
                creationDate = null;
                isExisting = false;
            }
        } catch (MongoException e) {
            throw new RuntimeException("Error while getting linked account for user " + riotPuuid, e);
        }
    }

    public boolean isExisting() {
        return isExisting;
    }

    public void update() {
        if(isExisting) {
            Bson updates = Updates.combine(
                    Updates.set(PUBLICLY_VISIBLE_FIELD, publiclyVisible),
                    Updates.set(DISCORD_USER_ID_FIELD, discordUserId),
                    Updates.set(RIOT_PUUID_FIELD, riotPuuid),
                    Updates.set(CREATION_DATE_FIELD, creationDate)
            );
            collection.updateOne(document, updates, new UpdateOptions().upsert(true));
        }
    }

    public long getDiscordUserId() {
        return discordUserId;
    }

    public String getRiotPuuid() {
        return riotPuuid;
    }

    public boolean isPubliclyVisible() {
        return publiclyVisible;
    }

    public RsoConnection setVisibleToPublic(boolean publiclyVisible) {
        this.publiclyVisible = publiclyVisible;
        return this;
    }

    public void delete() {
        collection.deleteOne(document);
    }

}
