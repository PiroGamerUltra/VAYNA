package dev.piste.vayna.util.templates;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.henrik.HenrikAPI;
import dev.piste.vayna.apis.henrik.gson.CurrentBundle;
import dev.piste.vayna.apis.henrik.gson.HenrikAccount;
import dev.piste.vayna.apis.henrik.gson.mmr.Rank;
import dev.piste.vayna.apis.henrik.gson.store.Item;
import dev.piste.vayna.apis.officer.OfficerAPI;
import dev.piste.vayna.apis.officer.gson.*;
import dev.piste.vayna.apis.officer.gson.competitivetier.Tier;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.mongodb.RsoConnection;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emojis;
import dev.piste.vayna.util.translations.Language;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class MessageEmbeds {

    public static MessageEmbed getNoConnectionEmbed(Language language, User user, Date expirationDate) {
        return new Embed()
                .setAuthor(user.getName(), user.getAvatarUrl())
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-connection-none-embed-title"))
                .setDescription(language.getTranslation("command-connection-none-embed-description")
                        .replaceAll("%expirationDate%", "<t:" + Math.round((float) expirationDate.getTime() / 1000) + ":R>"))
                .build();
    }

    public static MessageEmbed getPresentConnectionEmbed(Language language, User user, String riotId, boolean visibleToPublic) {
        return new Embed()
                .setAuthor(user.getName(), user.getAvatarUrl())
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-connection-present-embed-title"))
                .setDescription(language.getTranslation("command-connection-present-embed-description"))
                .addField(language.getTranslation("command-connection-present-embed-field-1-name"),
                        user.getAsMention() + " " + Emojis.getDiscord().getFormatted() + " \uD83D\uDD17 " + Emojis.getRiotGames().getFormatted() + " `" + riotId + "`", true)
                .addField(language.getTranslation("command-connection-present-embed-field-2-name"),
                        visibleToPublic ? "\uD83D\uDD13 " + language.getTranslation("command-connection-present-embed-field-2-text-public")
                                : "\uD83D\uDD12 " + language.getTranslation("command-connection-present-embed-field-2-text-private"), true)
                .build();
    }

    public static MessageEmbed getSettingsEmbed(Language language, Guild guild) {
        return new Embed()
                .setAuthor(guild.getName(), guild.getIconUrl())
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-settings-embed-title"))
                .addField(language.getTranslation("command-settings-embed-field-1-name"),
                        language.getTranslation("language-emoji") + " " + language.getTranslation("language-name"), false)
                .build();
    }

    public static ArrayList<MessageEmbed> getBundleEmbeds(Language language, CurrentBundle currentBundle) throws HttpErrorException, IOException, InterruptedException {
        OfficerAPI officerAPI = new OfficerAPI();
        ArrayList<MessageEmbed> embedList = new ArrayList<>();
        Bundle bundle = officerAPI.getBundle(currentBundle.getBundleUuid(), language.getLanguageCode());

        Embed bundleEmbed = new Embed()
                .setTitle(language.getEmbedTitlePrefix() + bundle.getDisplayName())
                .addField(language.getTranslation("command-store-embed-bundle-field-1-name"), currentBundle.getPrice() + " " + Emojis.getVP().getFormatted(), true)
                .addField(language.getTranslation("command-store-embed-bundle-field-2-name"), language.getTranslation("command-store-embed-bundle-field-2-text")
                        .replaceAll("%timestamp%", "<t:" + (Instant.now().getEpochSecond()+currentBundle.getSecondsRemaining()) + ":R>"), true)
                .setImage(bundle.getDisplayIcon())
                .removeFooter();
        embedList.add(bundleEmbed.build());

        for(Item item : currentBundle.getItems()) {
            Embed itemEmbed = new Embed()
                    .addField(language.getTranslation("command-store-embed-item-field-1-name"), item.getAmount() + "x", true)
                    .addField(language.getTranslation("command-store-embed-item-field-2-name"), item.getBasePrice() + " " + Emojis.getVP().getFormatted(), true)
                    .removeFooter();
            switch (item.getType()) {
                case "buddy" -> {
                    Buddy buddy = officerAPI.getBuddy(item.getUuid(), language.getLanguageCode());
                    itemEmbed.setTitle(language.getEmbedTitlePrefix() + buddy.getDisplayName())
                            .setThumbnail(buddy.getDisplayIcon());
                }
                case "player_card" -> {
                    Playercard playercard = officerAPI.getPlayercard(item.getUuid(), language.getLanguageCode());
                    itemEmbed.setTitle(language.getEmbedTitlePrefix() + playercard.getDisplayName())
                            .setThumbnail(playercard.getLargeArt())
                            .setImage(playercard.getWideArt());
                }
                case "spray" -> {
                    Spray spray = officerAPI.getSpray(item.getUuid(), language.getLanguageCode());
                    itemEmbed.setTitle(language.getEmbedTitlePrefix() + spray.getDisplayName())
                            .setThumbnail(spray.getAnimationGif() != null ? spray.getAnimationGif() : spray.getFullTransparentIcon());
                }
                case "skin_level" -> {
                    Skin skin = officerAPI.getSkin(item.getUuid(), language.getLanguageCode());
                    itemEmbed.setTitle(language.getEmbedTitlePrefix() + skin.getDisplayName())
                            .setImage(skin.getDisplayIcon());
                }
                default -> itemEmbed.setTitle(language.getEmbedTitlePrefix() + item.getName())
                        .setImage(item.getImage());
            }
            embedList.add(itemEmbed.build());
        }
        return embedList;
    }

    public static MessageEmbed getStatsEmbed(Language language, RsoConnection rsoConnection, RiotAccount riotAccount) throws HttpErrorException, IOException, InterruptedException {
        RiotAPI riotAPI = new RiotAPI();
        HenrikAPI henrikAPI = new HenrikAPI();

        String region = riotAPI.getRegion(riotAccount.getPuuid());
        HenrikAccount henrikAccount = henrikAPI.getAccount(riotAccount.getGameName(), riotAccount.getTagLine());
        Rank rank = henrikAPI.getMmr(henrikAccount.getPuuid(), henrikAccount.getRegion()).getRank();
        ArrayList<Tier> tiers = new OfficerAPI().getCompetitiveTier(language.getLanguageCode()).getTiers();

        Embed embed = new Embed()
                .setAuthor(riotAccount.getRiotId(), henrikAccount.getCard() != null ? henrikAccount.getCard().getSmall() : null)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-stats-embed-title"))
                .setDescription(language.getTranslation("command-stats-embed-description"))
                .addField(language.getTranslation("command-stats-embed-field-1-name"), Emojis.getLevel().getFormatted() + " " + henrikAccount.getAccountLevel(), true)
                .addField(language.getTranslation("command-stats-embed-field-2-name"), Emojis.getRegion(region).getFormatted() + " " + riotAPI.getRegionName(region), true);
        if(rsoConnection.isExisting()) {
            embed.addField(language.getTranslation("command-stats-embed-field-3-name"),
                    Emojis.getDiscord().getFormatted() + " " + Bot.getJDA().getUserById(rsoConnection.getDiscordUserId()).getAsMention() + " (`" + Bot.getJDA().getUserById(rsoConnection.getDiscordUserId()).getAsTag() + "`)", true);
        }

        if(rank.getCurrentTierPatched() == null) {
            Tier tier = tiers.get(0);
            embed.setThumbnail(tier.getLargeIcon())
                    .addField(language.getTranslation("command-stats-embed-field-4-name"), Emojis.getRankByTierName(tier.getTier()).getFormatted()  + " " + tier.getTierName(), false)
                    .addField(language.getTranslation("command-stats-embed-field-5-name"),
                            "**" + rank.getGamesNeededForRating() + "** " + (rank.getGamesNeededForRating()==1 ? language.getTranslation("command-stats-embed-field-5-text-1") : language.getTranslation("command-stats-embed-field-5-text-2")), true);
        } else {
            for (Tier tier : tiers) {
                if (tier.getTier() == rank.getCurrentTier()) {
                    embed.addField(language.getTranslation("command-stats-embed-field-4-name"), Emojis.getRankByTierName(tier.getTier()).getFormatted() + " " + tier.getTierName(), false)
                            .setThumbnail(tier.getLargeIcon());
                    if (rank.getCurrentTier() > 23) {
                        embed.addField(language.getTranslation("command-stats-embed-field-6-name"), "**" + rank.getRankingInTier() + "**RR » " +
                                (rank.getMmrChangeToLastGame() >= 0 ? Emojis.getIncrease().getFormatted() + " **+" + rank.getMmrChangeToLastGame() + "**" : Emojis.getDecrease().getFormatted() + " **" + rank.getMmrChangeToLastGame() + "**"), false);
                    } else {
                        embed.addField(language.getTranslation("command-stats-embed-field-6-name"), getProgressBar(rank.getRankingInTier()) + "\n" + "**" + rank.getRankingInTier() + "**/**100** » " +
                                (rank.getMmrChangeToLastGame() >= 0 ? Emojis.getIncrease().getFormatted() + " **+" + rank.getMmrChangeToLastGame() + "**" : Emojis.getDecrease().getFormatted() + " **" + rank.getMmrChangeToLastGame() + "**"), false);
                    }
                }
            }
        }
        return embed.build();
    }

    private static String getProgressBar(int ranking) {
        int value = ranking / 10;
        String greenStart = Emojis.getProgressBarGreen("start").getFormatted();
        String greenLine = Emojis.getProgressBarGreen("line").getFormatted();
        String greenEnd = Emojis.getProgressBarGreen("end").getFormatted();
        String redStart = Emojis.getProgressBarRed("start").getFormatted();
        String redLine = Emojis.getProgressBarRed("line").getFormatted();
        String redEnd = Emojis.getProgressBarRed("end").getFormatted();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                stringBuilder.append(value > 0 ? greenStart : redStart);
            } else if (i == 9) {
                stringBuilder.append(value > 8 ? greenEnd : redEnd);
            } else {
                stringBuilder.append(i < value ? greenLine : redLine);
            }
        }
        return stringBuilder.toString();
    }

}
