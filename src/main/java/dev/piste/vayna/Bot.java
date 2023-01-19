package dev.piste.vayna;

import dev.piste.vayna.config.TokensConfig;
import dev.piste.vayna.listener.SlashCommandListener;
import dev.piste.vayna.manager.CommandManager;
import dev.piste.vayna.mongodb.Mongo;
import dev.piste.vayna.config.TokenType;
import dev.piste.vayna.util.FontColor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import java.io.*;

public class Bot {

    private static JDA jda;

    public static JDA getJDA() {
        return jda;
    }

    public static boolean isDebug() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    public static String getConsolePrefix(String name) {
        return FontColor.WHITE + "[" + FontColor.PURPLE + name + FontColor.WHITE + "]" + FontColor.RESET + " ";
    }

    public static void main(String[] args) {
        try {
            new Bot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bot() {

        Mongo.connect();
        TokenType tokenType;
        if(isDebug()) {
            tokenType = TokenType.DEVELOPMENT;
        } else {
            tokenType = TokenType.PUBLIC;
        }
        String token = (String) TokensConfig.readToken(tokenType);

        jda = JDABuilder.createDefault(token)
                .addEventListeners(new SlashCommandListener())
                .setActivity(Activity.playing("VALORANT"))
                .setStatus(OnlineStatus.ONLINE)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableIntents(GatewayIntent.GUILD_PRESENCES)
                .build();

        CommandManager.createCommands();

        System.out.println(getConsolePrefix("Discord") + FontColor.GREEN + "Connected to " + FontColor.YELLOW + tokenType.toString().toUpperCase() + FontColor.GREEN + " instance" + FontColor.RESET);

        shutdownListener();

    }

    private void shutdownListener() {
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
