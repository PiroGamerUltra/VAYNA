package dev.piste.vayna.mongodb;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.piste.vayna.json.JSONTokens;
import dev.piste.vayna.util.Collection;
import dev.piste.vayna.util.Resources;
import dev.piste.vayna.util.FontColor;
import dev.piste.vayna.util.TokenType;
import org.bson.Document;

public class Mongo {

    private static MongoClient mongoClient;

    public static void connect() {

        try {
            ConnectionString connectionString = new ConnectionString((String) JSONTokens.readToken(TokenType.MONGODB));
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .serverApi(ServerApi.builder()
                            .version(ServerApiVersion.V1)
                            .build())
                    .build();
            mongoClient = MongoClients.create(settings);
            System.out.println(Resources.SYSTEM_PRINT_PREFIX + FontColor.GREEN + "Connected to " + FontColor.YELLOW + "MongoDB" + FontColor.GREEN + "." + FontColor.RESET);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }

    public static MongoDatabase getMongoDatabase() {
        return mongoClient.getDatabase("VAYNA");
    }

    public static MongoCollection<Document> getMongoCollection(Collection collection) {
        return getMongoDatabase().getCollection(collection.toString());
    }


}
