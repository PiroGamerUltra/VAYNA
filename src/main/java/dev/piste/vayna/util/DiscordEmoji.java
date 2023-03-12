package dev.piste.vayna.util;

import dev.piste.vayna.Bot;
import net.dv8tion.jda.api.entities.emoji.Emoji;

/**
 * @author Piste | https://github.com/PisteDev
 */
public enum DiscordEmoji {

    DISCORD(1067179191289794640L),
    RIOT_GAMES(1067160525487747192L),
    LEVEL(1067191892858646628L),
    GITHUB(1067215631214186556L),
    TOPGG(1067899900773273720L),
    VP(1068279053292941453L),
    PROGRESS_BAR_GREEN_START(1068975127456387212L),
    PROGRESS_BAR_GREEN_LINE(1068975125757702174L),
    PROGRESS_BAR_GREEN_END(1068975122586812437L),
    PROGRESS_BAR_RED_START(1068975194217128037L),
    PROGRESS_BAR_RED_LINE(1068975207831846982L),
    PROGRESS_BAR_RED_END(1068975224009281578L),
    INCREASE(1068996182979907704L),
    DECREASE(1068996186746396724L);

    private final long emojiId;

    DiscordEmoji(long emojiId) {
        this.emojiId = emojiId;
    }

    public Emoji getAsDiscordEmoji() {
        return Bot.getJDA().getEmojiById(emojiId);
    }

    public enum Rank {

        UNRANKED(0, 1068966345468817459L),
        IRON_1(3, 1068965683179827210L),
        IRON_2(4, 1068965679736295524L),
        IRON_3(5, 1068965675336482927L),
        BRONZE_1(6, 1068965669439291402L),
        BRONZE_2(7, 1068965664783609897L),
        BRONZE_3(8, 1068965659955970068L),
        SILVER_1(9, 1068965656189480960L),
        SILVER_2(10, 1068965651735117824L),
        SILVER_3(11, 1068965647058468917L),
        GOLD_1(12, 1068965641991749723L),
        GOLD_2(13, 1068965637277364274L),
        GOLD_3(14, 1068965632160309411L),
        PLATINUM_1(15, 1068965626468642828L),
        PLATINUM_2(16, 1068965622702149782L),
        PLATINUM_3(17, 1068965619531268146L),
        DIAMOND_1(18, 1068965614829457428L),
        DIAMOND_2(19, 1068965610161197168L),
        DIAMOND_3(20, 1068965606025597092L),
        ASCENDANT_1(21, 1068965601910984744L),
        ASCENDANT_2(22, 1068965598870118470L),
        ASCENDANT_3(23, 1068965596223508542L),
        IMMORTAL_1(24, 1068965592205373461L),
        IMMORTAL_2(25, 1068965586316558367L),
        IMMORTAL_3(26, 1068965581614755951L),
        RADIANT(27, 1068965576061505579L);

        private final int id;
        private final long emojiId;

        Rank(int id, long discordId) {
            this.id = id;
            this.emojiId = discordId;
        }

        public Emoji getAsDiscordEmoji() {
            return Bot.getJDA().getEmojiById(emojiId);
        }

        public static Rank getRankById(int rankId) {
            for (Rank rank : Rank.values()) {
                if (rank.id == rankId) {
                    return rank;
                }
            }
            return null;
        }

    }

    public enum Queue {

        COMPETITIVE("d2faff85-4964-f52e-b6b5-73a5d66ccad6", 1068966345468817459L),
        CUSTOM("63d60a3e-4838-695d-9077-e9af5ed523ca", 1075443080876007526L),
        DEATHMATCH("f3126c5e-4a6c-1f02-b216-cb9bf58df856", 1075443079366058014L),
        ESCALATION("3652def6-48fa-b868-61cc-d29702fc5dfa", 1075443076585226343L),
        REPLICATION("b1983fc3-4594-ce27-cfa8-8eb2a9b93018", 1075443075108851712L),
        SNOWBALL_FIGHT("154239f3-470c-612c-dd46-7db11282f208", 1075443070428008491L),
        SPIKE_RUSH("3938d5da-43bf-1e8c-f09f-858caf145975", 1075443072382533702L),
        SWIFTPLAY("2d257217-464c-7c4b-efc6-51a55365d44a", 1075443067760422954L),
        UNRATED("494b69f1-4e3a-1b03-c2cd-a4875d6e9cb6", 1075443080876007526L);

        private final String id;
        private final long emojiId;

        Queue(String id, long emojiId) {
            this.id = id;
            this.emojiId = emojiId;
        }

        public Emoji getAsDiscordEmoji() {
            return Bot.getJDA().getEmojiById(emojiId);
        }

        public static Queue getQueueById(String id) {
            for(Queue queue : Queue.values()) {
                if(queue.id.equals(id)) {
                    return queue;
                }
            }
            return null;
        }

    }

}