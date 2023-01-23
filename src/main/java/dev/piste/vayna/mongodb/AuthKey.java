package dev.piste.vayna.mongodb;

import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.entities.User;
import org.bson.Document;

import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class AuthKey {

    public static String get(User user) {
        MongoCollection<Document> authKeysCollection = Mongo.getAuthKeysCollection();
        Document foundAuthKeyDocument = authKeysCollection.find(eq("discordId", user.getIdLong())).first();
        String authKey;
        if(foundAuthKeyDocument != null) {
            authKey = (String) foundAuthKeyDocument.get("authKey");
        } else {
            authKey = UUID.randomUUID().toString();
            Document newAuthKeyDocument = new Document();
            newAuthKeyDocument.put("discordId", user.getIdLong());
            newAuthKeyDocument.put("authKey", authKey);
            authKeysCollection.insertOne(newAuthKeyDocument);
        }
        return authKey;
    }

}
