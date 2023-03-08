package dev.piste.vayna.util;

import dev.piste.vayna.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.emoji.Emoji;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("ALL")
public class Emojis {

    private static final Guild EMOJI_GUILD_1 = Bot.getJDA().getGuildById(1067157722967052402L);
    private static final Guild EMOJI_GUILD_2 = Bot.getJDA().getGuildById(1067158120670961785L);

    public static Emoji getRiotGames() {
        return EMOJI_GUILD_1.getEmojiById(1067160525487747192L);
    }

    public static Emoji getDiscord() {
        return EMOJI_GUILD_1.getEmojiById(1067179191289794640L);
    }

    public static Emoji getBlank() {
        return EMOJI_GUILD_1.getEmojiById(1067183520306442302L);
    }

    public static Emoji getLevel() {
        return EMOJI_GUILD_1.getEmojiById(1067191892858646628L);
    }

    public static Emoji getGitHub() {
        return EMOJI_GUILD_1.getEmojiById(1067215631214186556L);
    }

    public static Emoji getTopGG() {
        return EMOJI_GUILD_1.getEmojiById(1067899900773273720L);
    }

    public static Emoji getCheck() {
        return EMOJI_GUILD_1.getEmojiById(1068238386441236530L);
    }

    public static Emoji getCross() {
        return EMOJI_GUILD_1.getEmojiById(1068238620667957291L);
    }

    public static Emoji getVP() {
        return EMOJI_GUILD_1.getEmojiById(1068279053292941453L);
    }

    public static Emoji getRankByTierName(int tier) {
        Emoji emoji = null;
        switch (tier) {
            case 0 -> emoji = EMOJI_GUILD_2.getEmojiById(1068966345468817459L);
            case 3 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965683179827210L);
            case 4 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965679736295524L);
            case 5 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965675336482927L);
            case 6 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965669439291402L);
            case 7 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965664783609897L);
            case 8 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965659955970068L);
            case 9 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965656189480960L);
            case 10 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965651735117824L);
            case 11 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965647058468917L);
            case 12 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965641991749723L);
            case 13 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965637277364274L);
            case 14 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965632160309411L);
            case 15 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965626468642828L);
            case 16 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965622702149782L);
            case 17 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965619531268146L);
            case 18 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965614829457428L);
            case 19 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965610161197168L);
            case 20 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965606025597092L);
            case 21 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965601910984744L);
            case 22 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965598870118470L);
            case 23 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965596223508542L);
            case 24 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965592205373461L);
            case 25 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965586316558367L);
            case 26 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965581614755951L);
            case 27 -> emoji = EMOJI_GUILD_2.getEmojiById(1068965576061505579L);
        }
        return emoji;
    }

    public static Emoji getQueue(String queueName) {
        Emoji emoji = null;
        switch (queueName) {
            case "competitive" -> emoji = getRankByTierName(0);
            case "unrated", "newmap", "custom" -> emoji = EMOJI_GUILD_2.getEmojiById(1075443080876007526L);
            case "deathmatch" -> emoji = EMOJI_GUILD_2.getEmojiById(1075443079366058014L);
            case "ggteam" -> emoji = EMOJI_GUILD_2.getEmojiById(1075443076585226343L);
            case "onefa" -> emoji = EMOJI_GUILD_2.getEmojiById(1075443075108851712L);
            case "snowball" -> emoji = EMOJI_GUILD_2.getEmojiById(1075443070428008491L);
            case "spikerush" -> emoji = EMOJI_GUILD_2.getEmojiById(1075443072382533702L);
            case "swiftplay" -> emoji = EMOJI_GUILD_2.getEmojiById(1075443067760422954L);
        }
        return emoji;
    }

    public static Emoji getProgressBarGreen(String part) {
        switch (part) {
            case "start" -> {
                return EMOJI_GUILD_1.getEmojiById(1068975127456387212L);
            }
            case "line" -> {
                return EMOJI_GUILD_1.getEmojiById(1068975125757702174L);
            }
            case "end" -> {
                return EMOJI_GUILD_1.getEmojiById(1068975122586812437L);
            }
        }
        return null;
    }

    public static Emoji getProgressBarRed(String part) {
        switch (part) {
            case "start" -> {
                return EMOJI_GUILD_1.getEmojiById(1068975194217128037L);
            }
            case "line" -> {
                return EMOJI_GUILD_1.getEmojiById(1068975207831846982L);
            }
            case "end" -> {
                return EMOJI_GUILD_1.getEmojiById(1068975224009281578L);
            }
        }
        return null;
    }

    public static Emoji getIncrease() {
        return EMOJI_GUILD_1.getEmojiById(1068996182979907704L);
    }

    public static Emoji getDecrease() {
        return EMOJI_GUILD_1.getEmojiById(1068996186746396724L);
    }

    public static Emoji getRegion(String region) {
        String regionEmoji = switch (region) {
            case "eu" -> "\uD83C\uDDEA\uD83C\uDDFA";
            case "na" -> "\uD83C\uDDFA\uD83C\uDDF8";
            case "br", "latam" -> "\uD83C\uDDE7\uD83C\uDDF7";
            case "kr" -> "\uD83C\uDDF0\uD83C\uDDF7";
            case "ap" -> "\uD83C\uDDE6\uD83C\uDDFA";
            default -> "none";
        };
        return Emoji.fromUnicode(regionEmoji);
    }

}
