package dev.piste.vayna.interactions;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.henrik.HenrikAPI;
import dev.piste.vayna.apis.henrik.gson.HenrikAccount;
import dev.piste.vayna.apis.henrik.gson.mmr.Rank;
import dev.piste.vayna.apis.officer.OfficerAPI;
import dev.piste.vayna.apis.officer.gson.competitivetier.Tier;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.mongodb.RsoConnection;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emojis;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.ErrorMessages;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class StatsInteraction {

    public void perform(User executor, RiotAccount riotAccount, RsoConnection rsoConnection, InteractionHook interactionHook, Language language) throws IOException, HttpErrorException, InterruptedException {

        if(!rsoConnection.isExisting()) {
            if(rsoConnection.getDiscordUserId() != 0) {
                if (rsoConnection.getDiscordUserId() == executor.getIdLong()) {
                    interactionHook.editOriginalEmbeds(ErrorMessages.getNoConnectionSelf(language)).setActionRow(
                            Buttons.getSupportButton(language)
                    ).queue();
                } else {
                    interactionHook.editOriginalEmbeds(ErrorMessages.getNoConnection(Bot.getJDA().getUserById(rsoConnection.getDiscordUserId()).getAsMention(), language)).setActionRow(
                            Buttons.getSupportButton(language)
                    ).queue();
                }
                return;
            }
        } else {
            if(!rsoConnection.isPubliclyVisible() && (rsoConnection.getDiscordUserId() != executor.getIdLong())) {
                interactionHook.editOriginalEmbeds(ErrorMessages.getPrivate(language)).setActionRow(
                        Buttons.getSupportButton(language)
                ).queue();
                return;
            }
        }

        RiotAPI riotAPI = new RiotAPI();
        HenrikAPI henrikAPI = new HenrikAPI();

        String region;
        try {
            region = riotAPI.getRegion(riotAccount.getPuuid());
        } catch (HttpErrorException e) {
            if(e.getStatusCode() == 400 || e.getStatusCode() == 404) {
                interactionHook.editOriginalEmbeds(ErrorMessages.getInvalidRegion(riotAccount, language)).setActionRow(
                        Buttons.getSupportButton(language)
                ).queue();
                return;
            } else {
                throw e;
            }
        }
        HenrikAccount henrikAccount = henrikAPI.getAccount(riotAccount.getGameName(), riotAccount.getTagLine());
        Rank rank = henrikAPI.getMmr(henrikAccount.getPuuid(), henrikAccount.getRegion()).getRank();
        ArrayList<Tier> tiers = new OfficerAPI().getCompetitiveTier(language.getLanguageCode()).getTiers();

        Embed embed = new Embed()
                .setAuthor(riotAccount.getRiotId(), henrikAccount.getCard() != null ? henrikAccount.getCard().getSmall() : null)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-stats-embed-title"))
                .setDescription(language.getTranslation("command-stats-embed-description"))
                .addField(language.getTranslation("command-stats-embed-field-1-name"), Emojis.getLevel().getFormatted() + " " + henrikAccount.getAccountLevel(), true)
                .addField(language.getTranslation("command-stats-embed-field-2-name"), Emojis.getRegion(region).getFormatted() + " " + riotAPI.getPlatformData(region).getName(), true);
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

        interactionHook.editOriginalEmbeds(embed.build()).queue();
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