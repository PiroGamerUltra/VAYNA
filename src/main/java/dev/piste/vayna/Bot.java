package dev.piste.vayna;

import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.interactions.InteractionManager;
import dev.piste.vayna.listener.*;
import dev.piste.vayna.mongodb.Mongo;
import dev.piste.vayna.util.Logger;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class Bot {

    private static JDA jda;

    public static boolean isDebug() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    public static void main(String[] args) {
        ConfigManager.loadConfigs();
        Mongo.connect();
        LanguageManager.loadLanguages();

        jda = JDABuilder.createDefault(isDebug() ? ConfigManager.getTokensConfig().getBot().getDevelopment() : ConfigManager.getTokensConfig().getBot().getVayna())
                .addEventListeners(new InteractionListeners())
                .addEventListeners(new GuildJoinLeaveListener())
                .setActivity(Activity.competing("VALORANT"))
                .setStatus(OnlineStatus.ONLINE)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .build();

        // Registering all interactions
        InteractionManager.registerInteractions();

        new Bot().listenReload();

        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Logger(Bot.class).info("Started");
    }

    public static JDA getJDA() {
        return jda;
    }

    private void listenReload() {
        new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while (reader.readLine() != null) {
                    if (reader.readLine().equalsIgnoreCase("reload")) {
                        LanguageManager.loadLanguages();
                        ConfigManager.loadConfigs();
                        new Logger(Bot.class).info("Reloaded");
                    }
                }
            } catch (IOException ignored) {
            }
        }).start();
    }

}
