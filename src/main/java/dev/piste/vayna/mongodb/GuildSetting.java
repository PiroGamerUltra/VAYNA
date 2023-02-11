package dev.piste.vayna.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Piste | https://github.com/zPiste
 */
public class GuildSetting {

    private static final MongoCollection<Document> guildSettingsCollection = Mongo.getGuildSettingsCollection();
    private final Document guildSettingDocument;
    private final long guildId;
    private String language;

    public GuildSetting(long guildId) {
        this.guildId = guildId;
        guildSettingDocument = guildSettingsCollection.find(eq("guildId", guildId)).first();
        if(guildSettingDocument == null) {
            language = "en-US";
            insert();
        } else {
            language = (String) guildSettingDocument.get("language");
        }
    }

    public String getLanguage() {
        return language;
    }

    public GuildSetting setLanguage(String language) {
        this.language = language;
        return this;
    }

    public void update() {
        Bson updates = Updates.combine(
                Updates.set("language", language)
        );
        guildSettingsCollection.updateOne(guildSettingDocument, updates, new UpdateOptions().upsert(true));
    }

    private void insert() {
        Document newAuthKeyDocument = new Document();
        newAuthKeyDocument.put("guildId", guildId);
        newAuthKeyDocument.put("language", language);
        guildSettingsCollection.insertOne(newAuthKeyDocument);
    }

}
