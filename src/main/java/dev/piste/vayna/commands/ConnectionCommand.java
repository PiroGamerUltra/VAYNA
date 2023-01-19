package dev.piste.vayna.commands;

import com.mongodb.client.MongoCollection;
import dev.piste.vayna.config.Config;
import dev.piste.vayna.config.ConfigSetting;
import dev.piste.vayna.mongodb.Collection;
import dev.piste.vayna.mongodb.Mongo;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.bson.Document;

import java.util.Random;

public class ConnectionCommand {

    public static void performCommand(SlashCommandInteractionEvent event, String subcommand) {
        if(subcommand.equalsIgnoreCase("connect")) {
            MongoCollection<Document> mongoCollection = Mongo.getMongoCollection(Collection.AUTH_KEYS);

            String redirectUri = "https://piste.dev/VAYNA/RSO/redirect/";

            Document document = new Document();
            document.put("discordId", event.getUser().getIdLong());

            Document foundDocument = mongoCollection.find(document).first();
            String authKey;
            if(foundDocument != null) {
                authKey = (String) foundDocument.get("authKey");
            } else {
                authKey = getRandomHexString();
                document.put("authKey", authKey);
                mongoCollection.insertOne(document);
            }
            String authUri = redirectUri + "?authKey=" + authKey;

            Embed embed = new Embed();
            embed.setAuthor(event.getUser().getName(), Config.readSetting(ConfigSetting.WEBSITE_URI), event.getUser().getAvatarUrl());
            embed.setTitle("Authorization");
            embed.setDescription("[**Click here**](" + authUri + ") to link your Discord account to your Riot account");

            event.getHook().editOriginalEmbeds(embed.build()).queue();
        }


    }

    private static String getRandomHexString(){
        Random r = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        while(stringBuilder.length() < 32) {
            stringBuilder.append(Integer.toHexString(r.nextInt()));
        }
        return stringBuilder.substring(0, 32);
    }

}
