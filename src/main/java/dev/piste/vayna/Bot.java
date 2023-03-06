package dev.piste.vayna;

import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.interactions.InteractionManager;
import dev.piste.vayna.listener.GuildJoinLeaveListener;
import dev.piste.vayna.listener.InteractionListeners;
import dev.piste.vayna.mongodb.Mongo;
import dev.piste.vayna.util.Logger;
import dev.piste.vayna.translations.LanguageManager;
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
        registerInteractions();
        startReloadListener();
        startJDA();
    }

    private static void startJDA() {
        try {
            jda = JDABuilder.createDefault(isDebug() ? ConfigManager.getTokensConfig().getBot().getDevelopment() : ConfigManager.getTokensConfig().getBot().getVayna())
                    .addEventListeners(new InteractionListeners())
                    .addEventListeners(new GuildJoinLeaveListener())
                    .setActivity(Activity.competing("VALORANT"))
                    .setStatus(OnlineStatus.ONLINE)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .build();
            jda.awaitReady();
            registerInteractions();
        } catch (InterruptedException e) {
            new Logger(Bot.class).error("Error while starting JDA", e);
        }
    }

    private static void registerInteractions() {
        InteractionManager.registerInteractions();
    }

    private static void startReloadListener() {
        new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while (true) {
                    String input = reader.readLine();
                    if (input != null && input.equalsIgnoreCase("reload")) {
                        LanguageManager.loadLanguages();
                        ConfigManager.loadConfigs();
                        new Logger(Bot.class).info("Reloaded");
                    }
                }
            } catch (IOException e) {
                new Logger(Bot.class).error("Error reading console input", e);
            }
        }).start();
    }

    public static JDA getJDA() {
        return jda;
    }

}