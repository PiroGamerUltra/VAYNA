package dev.piste.vayna.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public class LinkedAccount {

    private long discordUserId;
    private String riotPuuid;
    private boolean visibleToPublic;
    private static final MongoCollection<Document> linkedAccountCollection = Mongo.getLinkedAccountCollection();
    private final Document linkedAccount;
    private final boolean isExisting;

    public LinkedAccount(long discordUserId) {
        this.discordUserId = discordUserId;
        linkedAccount = linkedAccountCollection.find(eq("discordUserId", discordUserId)).first();
        if(linkedAccount == null) {
            isExisting = false;
        } else {
            isExisting = true;
            riotPuuid = (String) linkedAccount.get("riotPuuid");
            visibleToPublic = (boolean) linkedAccount.get("public");
        }
    }

    public LinkedAccount(String riotPuuid) {
        this.riotPuuid = riotPuuid;
        linkedAccount = linkedAccountCollection.find(eq("riotPuuid", riotPuuid)).first();
        if(linkedAccount == null) {
            isExisting = false;
        } else {
            isExisting = true;
            discordUserId = (long) linkedAccount.get("discordUserId");
            visibleToPublic = (boolean) linkedAccount.get("public");
        }
    }

    public boolean isExisting() {
        return isExisting;
    }

    public void update() {
        if(isExisting) {
            Bson updates = Updates.combine(
                    Updates.set("public", visibleToPublic),
                    Updates.set("discordUserId", discordUserId),
                    Updates.set("riotPuuid", riotPuuid)
            );
            linkedAccountCollection.updateOne(linkedAccount, updates, new UpdateOptions().upsert(true));
        }
    }

    public long getDiscordUserId() {
        return discordUserId;
    }

    public String getRiotPuuid() {
        return riotPuuid;
    }

    public boolean isVisibleToPublic() {
        return visibleToPublic;
    }

    public LinkedAccount setVisibleToPublic(boolean visibleToPublic) {
        this.visibleToPublic = visibleToPublic;
        return this;
    }

    public void delete() {
        linkedAccountCollection.deleteOne(linkedAccount);
    }

}
