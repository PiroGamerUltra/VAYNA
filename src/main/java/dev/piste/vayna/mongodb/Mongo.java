package dev.piste.vayna.mongodb;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.piste.vayna.Bot;
import dev.piste.vayna.config.TokensConfig;
import dev.piste.vayna.util.FontColor;
import org.bson.Document;

public class Mongo {

    private static MongoClient mongoClient;

    public static void connect() {

        ConnectionString uri = new ConnectionString(new TokensConfig().getMongodbToken());
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(uri)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();

        try {
            mongoClient = MongoClients.create(settings);
            System.out.println(Bot.getConsolePrefix("MongoDB") + FontColor.GREEN + "Connected" + FontColor.RESET);
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public static MongoDatabase getMongoDatabase() {
        return mongoClient.getDatabase("VAYNA");
    }

    public static MongoCollection<Document> getLinkedAccountsCollection() {
        return getMongoDatabase().getCollection("linked-accounts");
    }

    public static MongoCollection<Document> getAuthKeysCollection() {
        return getMongoDatabase().getCollection("auth-keys");
    }


}
