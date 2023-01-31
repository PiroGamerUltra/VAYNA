package dev.piste.vayna.config.translations.buttons;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.henrik.HenrikAPI;
import dev.piste.vayna.apis.henrik.gson.HenrikAccount;
import dev.piste.vayna.apis.riotgames.gson.RiotAccount;
import dev.piste.vayna.apis.valorantapi.ValorantAPI;
import dev.piste.vayna.apis.valorantapi.gson.CompetitiveTier;
import dev.piste.vayna.apis.valorantapi.gson.competitivetier.Tier;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.config.translations.Language;
import dev.piste.vayna.config.translations.buttons.ranks.Errors;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.entities.MessageEmbed;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Rank {

    private String rank;
    private String gamesNeeded;
    private String game;
    private String games;
    private String rating;
    private Errors errors;

    public MessageEmbed getUnrankedMessageEmbed(RiotAccount riotAccount, HenrikAccount henrikAccount, Tier tier, Tier enUsTier, dev.piste.vayna.apis.henrik.gson.mmr.Rank rank) throws StatusCodeException {
        Embed embed = new Embed()
                .setAuthor(riotAccount.getRiotId(), Configs.getSettings().getWebsiteUri(), henrikAccount.getCard().getSmall())
                .setTitle(Configs.getTranslations().getTitlePrefix() + this.rank)
                .setColor(209, 54, 57)
                .addField(this.rank, Emoji.getRankByTierName(enUsTier.getTierName()).getFormatted()  + " " + tier.getTierName(), true)
                .setThumbnail(tier.getLargeIcon());
        if(enUsTier.getTierName().equalsIgnoreCase("Unranked")) {
            embed.addField(gamesNeeded, "**" + rank.getGamesNeededForRating() + "** " + (rank.getGamesNeededForRating()==1 ? game : games), true);
        } else {
            if(rank.getCurrentTier() > 23) {
                embed.addField(rating, "**" + rank.getRankingInTier() + "**RR » " +
                        (rank.getMmrChangeToLastGame()>=0 ? Emoji.getIncrease().getFormatted() + " **+" + rank.getMmrChangeToLastGame() + "**" : Emoji.getDecrease().getFormatted() + " **" + rank.getMmrChangeToLastGame() + "**"), false);
            } else {
                embed.addField(rating, getProgressBar(rank.getRankingInTier()) + "\n" + "**" + rank.getRankingInTier() + "**/**100** » " +
                        (rank.getMmrChangeToLastGame()>=0 ? Emoji.getIncrease().getFormatted() + " **+" + rank.getMmrChangeToLastGame() + "**" : Emoji.getDecrease().getFormatted() + " **" + rank.getMmrChangeToLastGame() + "**"), false);
            }
        }
        return embed.build();
    }

    public Errors getErrors() {
        return errors;
    }

    private String getProgressBar(int ranking) {
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
