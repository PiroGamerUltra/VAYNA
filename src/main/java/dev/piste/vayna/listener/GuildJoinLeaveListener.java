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
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Widget;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;
import org.bson.conversions.Bson;

public class GuildJoinLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        if(!System.getProperty("os.name").equalsIgnoreCase("Windows 10")) {
            writeInMongo(event.getJDA());
            writeInSupportDiscord(event.getJDA());
        }
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        if(!System.getProperty("os.name").equalsIgnoreCase("Windows 10")) {
            writeInMongo(event.getJDA());
            writeInSupportDiscord(event.getJDA());
        }
    }

    private static void writeInMongo(JDA jda) {
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

    private static void writeInSupportDiscord(JDA jda) {
        VoiceChannel serverCounterChannel = jda.getGuildById(882770046978494476l).getVoiceChannelById(883525942570549269l);
        serverCounterChannel.getManager().setName("\uD83D\uDD0B・Servers ➟ " + jda.getGuilds().size()).queue();
        VoiceChannel userCounterChannel = jda.getGuildById(882770046978494476l).getVoiceChannelById(883526077048299550l);
        int userCounter = 0;
        for(Guild guild : jda.getGuilds()) {
            userCounter += guild.getMemberCount();
        }
        userCounterChannel.getManager().setName("\uD83D\uDD0B・Users ➟ " + userCounter).queue();
    }

}
