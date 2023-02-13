package dev.piste.vayna.util.templates;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.henrik.HenrikAPI;
import dev.piste.vayna.apis.henrik.gson.HenrikAccount;
import dev.piste.vayna.apis.henrik.gson.mmr.Rank;
import dev.piste.vayna.apis.riot.InvalidRegionException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.apis.riot.gson.ActiveShard;
import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.apis.valorantapi.ValorantAPI;
import dev.piste.vayna.apis.valorantapi.gson.competitivetier.Tier;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;

/**
 * @author Piste | https://github.com/zPiste
 */
public class ReplyMessages {

    public static MessageEmbed getConnectionNone(Guild guild, User user) {
        Language language = LanguageManager.getLanguage(guild);
        Embed embed = new Embed();
        embed.setColor(209, 54, 57);
        embed.setAuthor(user.getName(), user.getAvatarUrl());
        embed.setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-connection-none-embed-title"));
        embed.setDescription(language.getTranslation("command-connection-none-embed-description"));
        return embed.build();
    }

    public static MessageEmbed getConnectionPresent(Guild guild, User user, String riotId, boolean visibleToPublic) {
        Language language = LanguageManager.getLanguage(guild);
        Embed embed = new Embed();
        embed.setAuthor(user.getName(), user.getAvatarUrl());
        embed.setColor(209, 54, 57);
        embed.setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-connection-present-embed-title"));
        embed.setDescription(language.getTranslation("command-connection-present-embed-description"));
        embed.addField(language.getTranslation("command-connection-present-embed-field-1-name"),
                user.getAsMention() + " " + Emoji.getDiscord().getFormatted() + " \uD83D\uDD17 " + Emoji.getRiotGames().getFormatted() + " `" + riotId + "`", true);
        embed.addField(language.getTranslation("command-connection-present-embed-field-2-name"),
                visibleToPublic ? "\uD83D\uDD13 " + language.getTranslation("command-connection-present-embed-field-2-text-public")
                        : "\uD83D\uDD12 " + language.getTranslation("command-connection-present-embed-field-2-text-private"), true);
        return embed.build();
    }

    public static MessageEmbed getSettings(Guild guild) {
        Language language = LanguageManager.getLanguage(guild);

        return new Embed()
                .setAuthor(guild.getName(), guild.getIconUrl())
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-settings-embed-title"))
                .addField(language.getTranslation("command-settings-embed-field-1-name"),
                        language.getTranslation("language-emoji") + " " + language.getTranslation("language-name"), false)
                .build();
    }


    public static MessageEmbed getStats(Guild guild, LinkedAccount linkedAccount, RiotAccount riotAccount) throws StatusCodeException, InvalidRegionException {
        Language language = LanguageManager.getLanguage(guild);
        ActiveShard activeShard = RiotAPI.getActiveShard(riotAccount.getPuuid());
        HenrikAccount henrikAccount = HenrikAPI.getAccountByRiotId(riotAccount.getGameName(), riotAccount.getTagLine());
        Rank rank = henrikAccount.getMmr().getRank();
        ArrayList<Tier> tiers = ValorantAPI.getCompetitiveTier(language.getLanguageCode()).getTiers();

        String regionEmoji = switch (activeShard.getActiveShard()) {
            case "eu" -> "\uD83C\uDDEA\uD83C\uDDFA";
            case "na" -> "\uD83C\uDDFA\uD83C\uDDF8";
            case "br", "latam" -> "\uD83C\uDDE7\uD83C\uDDF7";
            case "kr" -> "\uD83C\uDDF0\uD83C\uDDF7";
            case "ap" -> "\uD83C\uDDE6\uD83C\uDDFA";
            default -> "none";
        };
        String regionName = RiotAPI.getPlatformData(activeShard.getActiveShard()).getName();

        Embed embed = new Embed();
        embed.setAuthor(riotAccount.getRiotId(), henrikAccount.getCard() != null ? henrikAccount.getCard().getSmall() : null);
        embed.setColor(255, 70, 85);
        embed.setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-stats-embed-title"));
        embed.setDescription(language.getTranslation("command-stats-embed-description"));
        embed.addField(language.getTranslation("command-stats-embed-field-1-name"), Emoji.getLevel().getFormatted() + " " + henrikAccount.getAccountLevel(), true);
        embed.addField(language.getTranslation("command-stats-embed-field-2-name"), regionEmoji + " " + regionName, true);
        if(linkedAccount.isExisting()) {
            embed.addField(language.getTranslation("command-stats-embed-field-3-name"),
                    Emoji.getDiscord().getFormatted() + " " + Bot.getJDA().getUserById(linkedAccount.getDiscordUserId()).getAsMention() + " (`" + Bot.getJDA().getUserById(linkedAccount.getDiscordUserId()).getAsTag() + "`)", true);
        }

        if(rank.getCurrentTierPatched() == null) {
            Tier tier = tiers.get(0);
            embed.setThumbnail(tier.getLargeIcon())
                    .addField(language.getTranslation("button-rank-embed-field-1-name"), Emoji.getRankByTierName(tier.getTier()).getFormatted()  + " " + tier.getTierName(), false)
                    .addField(language.getTranslation("button-rank-embed-field-2-name"),
                            "**" + rank.getGamesNeededForRating() + "** " + (rank.getGamesNeededForRating()==1 ? language.getTranslation("button-rank-embed-field-2-text-1") : language.getTranslation("button-rank-embed-field-2-text-2")), true);
        } else {
            for (Tier tier : tiers) {
                if (tier.getTier() == rank.getCurrentTier()) {
                    embed.addField(language.getTranslation("button-rank-embed-field-1-name"), Emoji.getRankByTierName(tier.getTier()).getFormatted() + " " + tier.getTierName(), false)
                            .setThumbnail(tier.getLargeIcon());
                    if (rank.getCurrentTier() > 23) {
                        embed.addField(language.getTranslation("button-rank-embed-field-3-name"), "**" + rank.getRankingInTier() + "**RR » " +
                                (rank.getMmrChangeToLastGame() >= 0 ? Emoji.getIncrease().getFormatted() + " **+" + rank.getMmrChangeToLastGame() + "**" : Emoji.getDecrease().getFormatted() + " **" + rank.getMmrChangeToLastGame() + "**"), false);
                    } else {
                        embed.addField(language.getTranslation("button-rank-embed-field-3-name"), getProgressBar(rank.getRankingInTier()) + "\n" + "**" + rank.getRankingInTier() + "**/**100** » " +
                                (rank.getMmrChangeToLastGame() >= 0 ? Emoji.getIncrease().getFormatted() + " **+" + rank.getMmrChangeToLastGame() + "**" : Emoji.getDecrease().getFormatted() + " **" + rank.getMmrChangeToLastGame() + "**"), false);
                    }
                }
            }
        }
        return embed.build();
    }

    private static String getProgressBar(int ranking) {
        int value = (int) Math.round(ranking/10.0);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i<value; i++) {
            if(i==0) {
                stringBuilder.append(Emoji.getProgressBarGreen("start").getFormatted());
                continue;
            }
            if(i==9) {
                stringBuilder.append(Emoji.getProgressBarGreen("end").getFormatted());
                continue;
            }
            stringBuilder.append(Emoji.getProgressBarGreen("line").getFormatted());
        }
        for(int i = value; i<10; i++) {
            if(i==0) {
                stringBuilder.append(Emoji.getProgressBarRed("start").getFormatted());
                continue;
            }
            if(i==9) {
                stringBuilder.append(Emoji.getProgressBarRed("end").getFormatted());
                continue;
            }
            stringBuilder.append(Emoji.getProgressBarRed("line").getFormatted());
        }
        return stringBuilder.toString();
    }

}
