package dev.piste.vayna.mongodb;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class AuthKey {

    private static final MongoCollection<Document> authKeyCollection = Mongo.getAuthKeyCollection();
    private final long discordUserId;
    private final String authKey;

    public AuthKey(long discordUserId) {
        this.discordUserId = discordUserId;
        Document authKeyDocument = authKeyCollection.find(eq("discordUserId", discordUserId)).first();
        if(authKeyDocument == null) {
            authKey = UUID.randomUUID().toString();
            insert();
        } else {
            authKey = (String) authKeyDocument.get("authKey");
        }
    }

    public String getAuthKey() {
        return authKey;
    }

    private void insert() {
        Document newAuthKeyDocument = new Document();
        newAuthKeyDocument.put("discordUserId", discordUserId);
        newAuthKeyDocument.put("authKey", authKey);
        authKeyCollection.insertOne(newAuthKeyDocument);
    }

}
