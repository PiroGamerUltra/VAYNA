package dev.piste.vayna.mongodb;

import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.entities.User;
import org.bson.Document;

import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class AuthKey {

    private long discordUserId;
    private String authKey;
    private static final MongoCollection<Document> authKeyCollection = Mongo.getAuthKeyCollection();
    private Document authKeyDocument;
    private boolean isExisting;


    public AuthKey(long discordUserId) {
        this.discordUserId = discordUserId;
        authKeyDocument = authKeyCollection.find(eq("discordUserId", discordUserId)).first();
        if(authKeyDocument == null) {
            isExisting = false;
        } else {
            isExisting = true;
            this.authKey = (String) authKeyDocument.get("authKey");
        }
    }

    public AuthKey(String authKey) {
        this.authKey = authKey;
        authKeyDocument = authKeyCollection.find(eq("authKey", authKey)).first();
        if(authKeyDocument == null) {
            isExisting = false;
        } else {
            isExisting = true;
            this.discordUserId = (long) authKeyDocument.get("discordUserId");
        }
    }

    public boolean isExisting() {
        return isExisting;
    }

    public long getDiscordUserId() {
        if (isExisting) {
            return discordUserId;
        } else {
            return 0;
        }

    }

    public String getAuthKey() {
        if (!isExisting) {
            authKey = UUID.randomUUID().toString();
            insert(discordUserId, authKey);
        }
        return authKey;
    }

    public static void insert(long discordUserId, String authKey) {
        Document newAuthKeyDocument = new Document();
        newAuthKeyDocument.put("discordUserId", discordUserId);
        newAuthKeyDocument.put("authKey", authKey);
        authKeyCollection.insertOne(newAuthKeyDocument);
    }

    public void delete() {
        if(isExisting) {
            authKeyCollection.deleteOne(authKeyDocument);
        }
    }

}
