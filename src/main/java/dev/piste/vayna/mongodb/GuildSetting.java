package dev.piste.vayna.mongodb;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import dev.piste.vayna.translations.LanguageManager;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class GuildSetting {

    private static final String GUILD_ID_FIELD = "_id";
    private static final String LOCALE_FIELD = "locale";
    private static final MongoCollection<Document> COLLECTION = Mongo.getGuildSettingsCollection();

    private Document document;

    private final long guildId;
    private String locale;

    public GuildSetting(long guildId) {
        this.guildId = guildId;
        try (MongoCursor<Document> cursor = COLLECTION.find(eq(GUILD_ID_FIELD, guildId)).iterator()) {
            if (cursor.hasNext()) {
                document = cursor.next();
                locale = document.getString(LOCALE_FIELD);
            } else {
                locale = LanguageManager.getDefaultLanguage().getLocale();
                insert();
            }
        } catch (MongoException e) {
            throw new RuntimeException("Error while getting guild settings for guild " + guildId, e);
        }
    }

    public String getLocale() {
        return locale;
    }

    public GuildSetting setLocale(String locale) {
        this.locale = locale;
        return this;
    }

    public void update() {
        Bson updates = Updates.combine(
                Updates.set(GUILD_ID_FIELD, guildId),
                Updates.set(LOCALE_FIELD, locale)
        );
        COLLECTION.updateOne(eq(GUILD_ID_FIELD, guildId), updates, new UpdateOptions().upsert(true));
    }

    private void insert() {
        document = new Document()
                .append(GUILD_ID_FIELD, guildId)
                .append(LOCALE_FIELD, locale);
        COLLECTION.insertOne(document);
    }

}
