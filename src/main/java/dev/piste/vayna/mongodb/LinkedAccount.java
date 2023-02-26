package dev.piste.vayna.mongodb;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class LinkedAccount {

    private static final String DISCORD_USER_ID_FIELD = "discordUserId";
    private static final String RIOT_PUUID_FIELD = "riotPuuid";
    private static final String PUBLICLY_VISIBLE_FIELD = "publiclyVisible";
    private static final MongoCollection<Document> linkedAccountsCollection = Mongo.getLinkedAccountCollection();

    private final Document linkedAccountDocument;
    private final boolean isExisting;
    private final long discordUserId;
    private final String riotPuuid;
    private boolean publiclyVisible;


    public LinkedAccount(long discordUserId) {
        this.discordUserId = discordUserId;
        try (MongoCursor<Document> cursor = linkedAccountsCollection.find(eq(DISCORD_USER_ID_FIELD, discordUserId)).iterator()) {
            if (cursor.hasNext()) {
                linkedAccountDocument = cursor.next();
                riotPuuid = linkedAccountDocument.getString(RIOT_PUUID_FIELD);
                publiclyVisible = linkedAccountDocument.getBoolean(PUBLICLY_VISIBLE_FIELD);
                isExisting = true;
            } else {
                linkedAccountDocument = null;
                riotPuuid = null;
                publiclyVisible = false;
                isExisting = false;
            }
        } catch (MongoException e) {
            throw new RuntimeException("Error while getting linked account for user " + discordUserId, e);
        }
    }

    public LinkedAccount(String riotPuuid) {
        this.riotPuuid = riotPuuid;
        try (MongoCursor<Document> cursor = linkedAccountsCollection.find(eq(RIOT_PUUID_FIELD, riotPuuid)).iterator()) {
            if (cursor.hasNext()) {
                linkedAccountDocument = cursor.next();
                discordUserId = linkedAccountDocument.getLong(DISCORD_USER_ID_FIELD);
                publiclyVisible = linkedAccountDocument.getBoolean(PUBLICLY_VISIBLE_FIELD);
                isExisting = true;
            } else {
                linkedAccountDocument = null;
                discordUserId = 0L;
                publiclyVisible = false;
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
                    Updates.set(RIOT_PUUID_FIELD, riotPuuid)
            );
            linkedAccountsCollection.updateOne(linkedAccountDocument, updates, new UpdateOptions().upsert(true));
        }
    }

    public long getDiscordUserId() {
        return discordUserId;
    }

    public String getRiotPuuid() {
        return riotPuuid;
    }

    public boolean isVisibleToPublic() {
        return publiclyVisible;
    }

    public LinkedAccount setVisibleToPublic(boolean publiclyVisible) {
        this.publiclyVisible = publiclyVisible;
        return this;
    }

    public void delete() {
        linkedAccountsCollection.deleteOne(linkedAccountDocument);
    }

}
