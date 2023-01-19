package dev.piste.vayna.listener;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import dev.piste.vayna.commands.HelpCommand;
import dev.piste.vayna.commands.ConnectionCommand;
import dev.piste.vayna.Bot;
import dev.piste.vayna.mongodb.Mongo;
import dev.piste.vayna.mongodb.Collection;
import dev.piste.vayna.util.FontColor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;
import org.bson.conversions.Bson;

public class SlashCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if(!Bot.isDebug()) {
            log();
        }
        System.out.println(event.getName());
        switch (event.getName().toLowerCase()) {
            case "help" -> {
                event.deferReply().setEphemeral(true).queue();
                HelpCommand.performCommand(event);
            }
            case "connection" -> {
                event.deferReply().setEphemeral(true).queue();
                ConnectionCommand.performCommand(event, event.getSubcommandName());
            }
        }

    }

    private void log() {
        MongoCollection<Document> statsCollection = Mongo.getMongoCollection(Collection.BOT_STATS);
        Document query = new Document().append("type", "commands");
        Document document = statsCollection.find(query).first();
        int counter = document.getInteger("counter");
        Bson updates = Updates.set("counter", counter+1);
        UpdateOptions updateOptions = new UpdateOptions().upsert(true);

        try {
            statsCollection.updateOne(query, updates, updateOptions);
        } catch (MongoException e) {
            System.out.println(Bot.getConsolePrefix("ERROR") + FontColor.RED + "Error while updating Command Counter.");
        }
    }

}
