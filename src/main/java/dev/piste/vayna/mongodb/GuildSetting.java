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
    private final boolean isExisting;

    public GuildSetting(long guildId) {
        this.guildId = guildId;
        guildSettingDocument = guildSettingsCollection.find(eq("guildId", guildId)).first();
        if(guildSettingDocument == null) {
            isExisting = false;
        } else {
            isExisting = true;
            this.language = (String) guildSettingDocument.get("language");
        }
    }

    public boolean isExisting() {
        return isExisting;
    }

    public String getLanguage() {
        if (!isExisting) {
            language = "en-US";
            insert(guildId, language);
        }
        return language;
    }

    public void update(String language) {
        if(isExisting) {
            this.language = language;
            Bson updates = Updates.set("language", language);
            UpdateOptions options = new UpdateOptions().upsert(true);
            guildSettingsCollection.updateOne(guildSettingDocument, updates, options);
        }
    }

    public static void insert(long guildId, String language) {
        Document newAuthKeyDocument = new Document();
        newAuthKeyDocument.put("guildId", guildId);
        newAuthKeyDocument.put("language", language);
        guildSettingsCollection.insertOne(newAuthKeyDocument);
    }

    public void delete() {
        if(isExisting) {
            guildSettingsCollection.deleteOne(guildSettingDocument);
        }
    }

}
