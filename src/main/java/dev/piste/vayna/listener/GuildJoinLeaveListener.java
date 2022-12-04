package dev.piste.vayna.listener;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import dev.piste.vayna.mongodb.Mongo;
import dev.piste.vayna.util.Collection;
import dev.piste.vayna.util.FontColor;
import dev.piste.vayna.util.Resources;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;
import org.bson.conversions.Bson;

public class GuildJoinLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        joinAndLeave(event.getJDA());
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        joinAndLeave(event.getJDA());
    }

    private static void joinAndLeave(JDA jda) {
        MongoCollection<Document> statsCollection = Mongo.getMongoCollection(Collection.BOT_STATS);
        Document query = new Document().append("type", "servers");
        Bson updates = Updates.set("counter", jda.getGuilds().size());
        UpdateOptions updateOptions = new UpdateOptions().upsert(true);

        try {
            statsCollection.updateOne(query, updates, updateOptions);
        } catch (MongoException e) {
            System.out.println(Resources.SYSTEM_PRINT_PREFIX + FontColor.RED + "Error while updating Server Counter.");
        }
    }

}
