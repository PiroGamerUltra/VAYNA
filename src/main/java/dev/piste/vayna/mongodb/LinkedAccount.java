package dev.piste.vayna.mongodb;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.print.Doc;

import static com.mongodb.client.model.Filters.eq;

public class LinkedAccount {

    private long discordUserId;
    private String riotPuuid;
    private static final MongoCollection<Document> linkedAccountsCollection = Mongo.getLinkedAccountsCollection();
    private Document linkedAccount;
    private boolean isExisting;

    public LinkedAccount(long discordUserId) {
        this.discordUserId = discordUserId;
        linkedAccount = linkedAccountsCollection.find(eq("discordUserId", discordUserId)).first();
        if(linkedAccount == null) {
            isExisting = false;
        } else {
            isExisting = true;
        }
    }

    public LinkedAccount(String riotPuuid) {
        this.riotPuuid = riotPuuid;
        linkedAccount = linkedAccountsCollection.find(eq("riotPuuid", riotPuuid)).first();
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

    public Document get() {
        if(isExisting) {
            return linkedAccount;
        } else {
            return null;
        }
    }

    public void delete() {
        if(isExisting) {
            linkedAccountsCollection.deleteOne(linkedAccount);
        }
    }

}
