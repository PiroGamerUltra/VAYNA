package dev.piste.vayna.mongodb;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class GuildSetting {

    private static final String GUILD_ID_FIELD = "_id";
    private static final String LANGUAGE_CODE_FIELD = "languageCode";
    private static final MongoCollection<Document> COLLECTION = Mongo.getGuildSettingsCollection();

    private final long guildId;
    private String languageCode;

    public GuildSetting(long guildId) {
        this.guildId = guildId;
        try (MongoCursor<Document> cursor = COLLECTION.find(eq(GUILD_ID_FIELD, guildId)).iterator()) {
            if (cursor.hasNext()) {
                Document document = cursor.next();
                languageCode = document.getString(LANGUAGE_CODE_FIELD);
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
        Bson updates = Updates.combine(
                Updates.set(GUILD_ID_FIELD, guildId),
                Updates.set(LANGUAGE_CODE_FIELD, languageCode)
        );
        COLLECTION.updateOne(eq(GUILD_ID_FIELD, guildId), updates, new UpdateOptions().upsert(true));
    }

    private void insert() {
        Document newDocument = new Document()
                .append(GUILD_ID_FIELD, guildId)
                .append(LANGUAGE_CODE_FIELD, languageCode);
        COLLECTION.insertOne(newDocument);
    }

}
