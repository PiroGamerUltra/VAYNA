package dev.piste.vayna.buttons;

import com.mongodb.client.MongoCollection;
import dev.piste.vayna.config.SettingsConfig;
import dev.piste.vayna.embeds.ConnectionEmbed;
import dev.piste.vayna.mongodb.Mongo;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.bson.Document;

import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class DisconnectRiotAccount {

    public static void performButton(ButtonInteractionEvent event) {
        MongoCollection<Document> linkedAccountsCollection = Mongo.getLinkedAccountsCollection();
        Document foundAccount = linkedAccountsCollection.find(eq("discordId", event.getUser().getIdLong())).first();

        if(foundAccount != null) {
            linkedAccountsCollection.deleteOne(eq("discordId", event.getUser().getIdLong()));
        }
        MongoCollection<Document> authKeysCollection = Mongo.getAuthKeysCollection();
        Document foundAuthKeyDocument = authKeysCollection.find(eq("discordId", event.getUser().getIdLong())).first();
        String authKey;
        if(foundAuthKeyDocument != null) {
            authKey = (String) foundAuthKeyDocument.get("authKey");
        } else {
            authKey = UUID.randomUUID().toString();
            Document newAuthKeyDocument = new Document();
            newAuthKeyDocument.put("discordId", event.getUser().getIdLong());
            newAuthKeyDocument.put("authKey", authKey);
            authKeysCollection.insertOne(newAuthKeyDocument);
        }

        event.replyEmbeds(ConnectionEmbed.getNoConnectionPresent(event.getUser().getAsTag())).setActionRow(
                Button.link(SettingsConfig.getWebsiteUri() + "/RSO/redirect/?authKey=" + authKey, "Connect").withEmoji(Emoji.getRiotGames())
        ).setEphemeral(true).queue();
    }

}
