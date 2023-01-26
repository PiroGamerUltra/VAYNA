package dev.piste.vayna.mongodb;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class LinkedAccount {

    private long discordUserId;
    private String riotPuuid;
    private static final MongoCollection<Document> linkedAccountCollection = Mongo.getLinkedAccountCollection();
    private Document linkedAccount;
    private boolean isExisting;

    public LinkedAccount(long discordUserId) {
        this.discordUserId = discordUserId;
        linkedAccount = linkedAccountCollection.find(eq("discordUserId", discordUserId)).first();
        if(linkedAccount == null) {
            isExisting = false;
        } else {
            isExisting = true;
            this.riotPuuid = (String) linkedAccount.get("riotPuuid");
        }
    }

    public LinkedAccount(String riotPuuid) {
        this.riotPuuid = riotPuuid;
        linkedAccount = linkedAccountCollection.find(eq("riotPuuid", riotPuuid)).first();
        if(linkedAccount == null) {
            isExisting = false;
        } else {
            isExisting = true;
            this.discordUserId = (long) linkedAccount.get("discordUserId");
        }
    }

    public boolean isExisting() {
        return isExisting;
    }

    public long getDiscordUserId() {
        if(isExisting) {
            return discordUserId;
        } else {
            return 0;
        }
    }

    public String getRiotPuuid() {
        if(isExisting) {
            return riotPuuid;
        } else {
            return null;
        }
    }

    public void delete() {
        if(isExisting) {
            linkedAccountCollection.deleteOne(linkedAccount);
        }
    }

    public static void insert(long discordUserId, String riotPuuid) {
        Document newLinkedAccountDocument = new Document();
        newLinkedAccountDocument.put("discordUserId", discordUserId);
        newLinkedAccountDocument.put("riotPuuid", riotPuuid);
        linkedAccountCollection.insertOne(newLinkedAccountDocument);
    }

}
