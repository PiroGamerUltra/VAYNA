package dev.piste.vayna.mongodb;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import net.dv8tion.jda.api.entities.User;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Date;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class RSOConnection {

    private static final String DISCORD_USER_ID_FIELD = "_id";
    private static final String RIOT_PUUID_FIELD = "riotPuuid";
    private static final String PUBLICLY_VISIBLE_FIELD = "publiclyVisible";
    private static final String CREATION_DATE_FIELD = "creationDate";
    private static final MongoCollection<Document> COLLECTION = Mongo.getRsoConnectionsCollection();

    private final Document document;
    private final boolean isExisting;
    private final long discordUserId;
    private final String riotPuuid;
    private boolean publiclyVisible;
    private final Date creationDate;

    private User user;

    public RSOConnection(long discordUserId) {
        this.discordUserId = discordUserId;
        try (MongoCursor<Document> cursor = COLLECTION.find(eq(DISCORD_USER_ID_FIELD, discordUserId)).iterator()) {
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

    public RSOConnection(String riotPuuid) {
        this.riotPuuid = riotPuuid;
        try (MongoCursor<Document> cursor = COLLECTION.find(eq(RIOT_PUUID_FIELD, riotPuuid)).iterator()) {
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
        if (isExisting) {
            Bson updates = Updates.combine(
                    Updates.set(DISCORD_USER_ID_FIELD, discordUserId),
                    Updates.set(RIOT_PUUID_FIELD, riotPuuid),
                    Updates.set(PUBLICLY_VISIBLE_FIELD, publiclyVisible),
                    Updates.set(CREATION_DATE_FIELD, creationDate)
            );
            COLLECTION.updateOne(document, updates, new UpdateOptions().upsert(true));
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

    public RSOConnection setVisibleToPublic(boolean publiclyVisible) {
        this.publiclyVisible = publiclyVisible;
        return this;
    }

    public void delete() {
        COLLECTION.deleteOne(document);
    }

    public RSOConnection setUser(User user) {
        this.user = user;
        return this;
    }

    public User getUser() {
        return user;
    }

}
