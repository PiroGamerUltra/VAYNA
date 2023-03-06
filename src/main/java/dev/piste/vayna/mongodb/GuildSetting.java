package dev.piste.vayna.mongodb;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class GuildSetting {

    private static final MongoCollection<Document> guildSettingsCollection = Mongo.getGuildSettingsCollection();
    private static final String GUILD_ID_FIELD = "_id";
    private static final String LANGUAGE_CODE_FIELD = "languageCode";

    private final long guildId;
    private String languageCode;

    public GuildSetting(long guildId) {
        this.guildId = guildId;
        try (MongoCursor<Document> cursor = guildSettingsCollection.find(eq(GUILD_ID_FIELD, guildId)).iterator()) {
            if (cursor.hasNext()) {
                Document guildSettingsDocument = cursor.next();
                languageCode = guildSettingsDocument.getString(LANGUAGE_CODE_FIELD);
            } else {
                languageCode = "en-US";
                insert();
            }
        } catch (MongoException e) {
            throw new RuntimeException("Error while getting guild settings for guild " + guildId, e);
        }
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public GuildSetting setLanguage(String languageCode) {
        this.languageCode = languageCode;
        return this;
    }

    public void update() {
        guildSettingsCollection.updateOne(eq(GUILD_ID_FIELD, guildId), set(LANGUAGE_CODE_FIELD, languageCode), new UpdateOptions().upsert(true));
    }

    private void insert() {
        Document newAuthKeyDocument = new Document();
        newAuthKeyDocument.put(GUILD_ID_FIELD, guildId);
        newAuthKeyDocument.put(LANGUAGE_CODE_FIELD, languageCode);
        guildSettingsCollection.insertOne(newAuthKeyDocument);
    }

}
