package dev.piste.vayna.util;

import dev.piste.vayna.Bot;
import net.dv8tion.jda.api.entities.Guild;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("ALL")
public class Emoji {

    private static final Guild EMOJI_GUILD_1 = Bot.getJDA().getGuildById(1067157722967052402L);
    private static final Guild EMOJI_GUILD_2 = Bot.getJDA().getGuildById(1067158120670961785L);

    public static net.dv8tion.jda.api.entities.emoji.Emoji getRiotGames() {
        return EMOJI_GUILD_1.getEmojiById(1067160525487747192L);
    }

    public static net.dv8tion.jda.api.entities.emoji.Emoji getDiscord() {
        return EMOJI_GUILD_1.getEmojiById(1067179191289794640L);
    }

    public static net.dv8tion.jda.api.entities.emoji.Emoji getBlank() {
        return EMOJI_GUILD_1.getEmojiById(1067183520306442302L);
    }

    public static net.dv8tion.jda.api.entities.emoji.Emoji getLevel() {
        return EMOJI_GUILD_1.getEmojiById(1067191892858646628L);
    }

    public static net.dv8tion.jda.api.entities.emoji.Emoji getGitHub() {
        return EMOJI_GUILD_1.getEmojiById(1067215631214186556L);
    }

    public static net.dv8tion.jda.api.entities.emoji.Emoji getTopGG() {
        return EMOJI_GUILD_1.getEmojiById(1067899900773273720L);
    }

    public static net.dv8tion.jda.api.entities.emoji.Emoji getCheck() {
        return EMOJI_GUILD_1.getEmojiById(1068238386441236530L);
    }

    public static net.dv8tion.jda.api.entities.emoji.Emoji getCross() {
        return EMOJI_GUILD_1.getEmojiById(1068238620667957291L);
    }

    public static net.dv8tion.jda.api.entities.emoji.Emoji getVP() {
        return EMOJI_GUILD_1.getEmojiById(1068279053292941453L);
    }

    public static net.dv8tion.jda.api.entities.emoji.Emoji getRankByTierName(int tier) {
        net.dv8tion.jda.api.entities.emoji.Emoji emoji = null;
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

    public static net.dv8tion.jda.api.entities.emoji.Emoji getQueue(String queueUuid) {
        net.dv8tion.jda.api.entities.emoji.Emoji emoji = null;
        switch (queueUuid) {
            case "d2faff85-4964-f52e-b6b5-73a5d66ccad6" -> emoji = getRankByTierName(0);
            case "63d60a3e-4838-695d-9077-e9af5ed523ca", "6ca8aa97-413c-241b-8927-d5bd1661c1d4", "494b69f1-4e3a-1b03-c2cd-a4875d6e9cb6" -> emoji = EMOJI_GUILD_2.getEmojiById(1075443080876007526L);
            case "f3126c5e-4a6c-1f02-b216-cb9bf58df856" -> emoji = EMOJI_GUILD_2.getEmojiById(1075443079366058014L);
            case "3652def6-48fa-b868-61cc-d29702fc5dfa" -> emoji = EMOJI_GUILD_2.getEmojiById(1075443076585226343L);
            case "b1983fc3-4594-ce27-cfa8-8eb2a9b93018" -> emoji = EMOJI_GUILD_2.getEmojiById(1075443075108851712L);
            case "154239f3-470c-612c-dd46-7db11282f208" -> emoji = EMOJI_GUILD_2.getEmojiById(1075443070428008491L);
            case "3938d5da-43bf-1e8c-f09f-858caf145975" -> emoji = EMOJI_GUILD_2.getEmojiById(1075443072382533702L);
            case "2d257217-464c-7c4b-efc6-51a55365d44a" -> emoji = EMOJI_GUILD_2.getEmojiById(1075443067760422954L);
        }
        return emoji;
    }

    public static net.dv8tion.jda.api.entities.emoji.Emoji getProgressBarGreen(String part) {
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

    public static net.dv8tion.jda.api.entities.emoji.Emoji getProgressBarRed(String part) {
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

    public static net.dv8tion.jda.api.entities.emoji.Emoji getIncrease() {
        return EMOJI_GUILD_1.getEmojiById(1068996182979907704L);
    }

    public static net.dv8tion.jda.api.entities.emoji.Emoji getDecrease() {
        return EMOJI_GUILD_1.getEmojiById(1068996186746396724L);
    }

}
