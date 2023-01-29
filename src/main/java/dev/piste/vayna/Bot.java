package dev.piste.vayna;

import dev.piste.vayna.apis.riotgames.gson.RiotAccount;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.config.tokens.TokensConfig;
import dev.piste.vayna.listener.ButtonInteractionListener;
import dev.piste.vayna.listener.GuildJoinLeaveListener;
import dev.piste.vayna.listener.ModalInteractionListener;
import dev.piste.vayna.listener.SlashCommandListener;
import dev.piste.vayna.manager.CommandManager;
import dev.piste.vayna.mongodb.Mongo;
import dev.piste.vayna.util.FontColor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Bot {

    private static JDA jda;

    public static JDA getJDA() {
        return jda;
    }

    public static boolean isDebug() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    private static final Map<String, RiotAccount> statsButtonMap = new HashMap<>();

    public static String getConsolePrefix(String name) {
        return FontColor.WHITE + "[" + FontColor.PURPLE + name + FontColor.WHITE + "]" + FontColor.RESET + " ";
    }

    public static Map<String, RiotAccount> getStatsButtonMap() {
        return statsButtonMap;
    }

    public static void main(String[] args) {
        Mongo.connect();

        TokensConfig tokensConfig = Configs.getTokens();

        jda = JDABuilder.createDefault(isDebug() ? tokensConfig.getBot().getDevelopment() : tokensConfig.getBot().getVayna())
                .addEventListeners(new SlashCommandListener())
                .addEventListeners(new GuildJoinLeaveListener())
                .addEventListeners(new ButtonInteractionListener())
                .addEventListeners(new ModalInteractionListener())
                .setActivity(Activity.competing("VALORANT"))
                .setStatus(OnlineStatus.ONLINE)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableIntents(GatewayIntent.GUILD_PRESENCES)
                .build();

        CommandManager.registerCommands();

        System.out.println(getConsolePrefix("Discord") + FontColor.GREEN + "Connected" + FontColor.RESET);

        new Bot().listenShutdown();
    }



    private void listenShutdown() {
        new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while (reader.readLine() != null) {
                    if (reader.readLine().equalsIgnoreCase("exit")) {
                        if (jda != null) {
                            jda.shutdown();
                            System.out.println(getConsolePrefix("VAYNA") + FontColor.GREEN + "Stopped" + FontColor.RESET);
                        }
                        reader.close();
                    } else {
                        System.out.println(getConsolePrefix("VAYNA") + FontColor.YELLOW + "Type 'exit' to stop the bot." + FontColor.RESET);
                    }
                }
            } catch (IOException ignored) {
            }
        }).start();
    }

}
