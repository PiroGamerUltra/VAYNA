package dev.piste.vayna.mongodb;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class AuthKey {

    private final long discordUserId;
    private String authKey;
    private static final MongoCollection<Document> authKeyCollection = Mongo.getAuthKeyCollection();
    private final boolean isExisting;


    public AuthKey(long discordUserId) {
        this.discordUserId = discordUserId;
        Document authKeyDocument = authKeyCollection.find(eq("discordUserId", discordUserId)).first();
        if(authKeyDocument == null) {
            isExisting = false;
        } else {
            isExisting = true;
            this.authKey = (String) authKeyDocument.get("authKey");
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

}
