package dev.piste.vayna.mongodb;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.piste.vayna.Bot;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.config.TokensConfig;
import dev.piste.vayna.util.ConsoleColor;
import org.bson.Document;

import java.util.Collections;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class Mongo {

    private static MongoDatabase mongoDatabase;

    public static void connect() {

        TokensConfig.MongoCredentials mongoCredentials = ConfigManager.getTokensConfig().getMongoCreds();

        MongoCredential credential = MongoCredential.createCredential(mongoCredentials.getUsername(), mongoCredentials.getAuthDb(), mongoCredentials.getPassword().toCharArray());
        MongoClientSettings settings =
                MongoClientSettings.builder().credential(credential).applyToClusterSettings(builder ->
                        builder.hosts(Collections.singletonList(new ServerAddress(mongoCredentials.getHost(), mongoCredentials.getPort())))).build();

        MongoClient mongoClient = MongoClients.create(settings);
        mongoDatabase = mongoClient.getDatabase("vayna");

        System.out.println(Bot.getConsolePrefix("MongoDB") + ConsoleColor.GREEN + "Connected" + ConsoleColor.RESET);
    }

    public static MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public static MongoCollection<Document> getLinkedAccountCollection() {
        return getMongoDatabase().getCollection("linkedAccounts");
    }

    public static MongoCollection<Document> getAuthKeyCollection() {
        return getMongoDatabase().getCollection("authKeys");
    }

    public static MongoCollection<Document> getGuildSettingsCollection() {
        return getMongoDatabase().getCollection("guildSettings");
    }


}
