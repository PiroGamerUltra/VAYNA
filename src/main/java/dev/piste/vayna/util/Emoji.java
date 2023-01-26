package dev.piste.vayna.util;

import dev.piste.vayna.Bot;
import net.dv8tion.jda.api.entities.Guild;

public class Emoji {

    private static Guild emojiGuild1 = Bot.getJDA().getGuildById(1067157722967052402L);
    private static Guild emojiGuild2 = Bot.getJDA().getGuildById(1067158120670961785L);

    public static net.dv8tion.jda.api.entities.emoji.Emoji getRiotGames() {
        return emojiGuild1.getEmojiById(1067160525487747192L);
    }

    public static net.dv8tion.jda.api.entities.emoji.Emoji getDiscord() {
        return emojiGuild1.getEmojiById(1067179191289794640L);
    }

    public static net.dv8tion.jda.api.entities.emoji.Emoji getBlank() {
        return emojiGuild1.getEmojiById(1067183520306442302L);
    }

    public static net.dv8tion.jda.api.entities.emoji.Emoji getLevel() {
        return emojiGuild1.getEmojiById(1067191892858646628L);
    }

    public static net.dv8tion.jda.api.entities.emoji.Emoji getGitHub() {
        return emojiGuild1.getEmojiById(1067215631214186556L);
    }

    public static net.dv8tion.jda.api.entities.emoji.Emoji getTopGG() {
        return emojiGuild1.getEmojiById(1067899900773273720L);
    }

    public static net.dv8tion.jda.api.entities.emoji.Emoji getCheck() {
        return emojiGuild1.getEmojiById(1068238386441236530L);
    }

    public static net.dv8tion.jda.api.entities.emoji.Emoji getCross() {
        return emojiGuild1.getEmojiById(1068238620667957291L);
    }

    public static net.dv8tion.jda.api.entities.emoji.Emoji getVP() {
        return emojiGuild1.getEmojiById(1068279053292941453L);
    }

}
