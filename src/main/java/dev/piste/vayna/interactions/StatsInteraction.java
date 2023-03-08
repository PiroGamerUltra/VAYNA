package dev.piste.vayna.interactions;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.HenrikAPI;
import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.OfficerAPI;
import dev.piste.vayna.apis.RiotGamesAPI;
import dev.piste.vayna.apis.entities.henrik.HenrikAccount;
import dev.piste.vayna.apis.entities.henrik.MMR;
import dev.piste.vayna.apis.entities.officer.Rank;
import dev.piste.vayna.apis.entities.riotgames.RiotAccount;
import dev.piste.vayna.mongodb.RsoConnection;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emojis;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.ErrorMessages;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.io.IOException;
import java.util.List;

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

        RiotGamesAPI riotGamesAPI = new RiotGamesAPI();
        HenrikAPI henrikAPI = new HenrikAPI();

        String region;
        try {
            region = riotGamesAPI.getRegion(riotAccount.getPUUID());
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
        HenrikAccount henrikAccount = henrikAPI.getAccount(riotAccount.getName(), riotAccount.getTag());
        MMR.CurrentData currentMmrData = henrikAPI.getMMR(henrikAccount.getId(), henrikAccount.getRegion()).getCurrentData();
        List<Rank> ranks = new OfficerAPI().getRanks(language.getLanguageCode());

        Embed embed = new Embed()
                .setAuthor(riotAccount.getRiotId(), henrikAccount.getPlayerCard() != null ? henrikAccount.getPlayerCard().getSmallArt() : null)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-stats-embed-title"))
                .setDescription(language.getTranslation("command-stats-embed-description"))
                .addField(language.getTranslation("command-stats-embed-field-1-name"), Emojis.getLevel().getFormatted() + " " + henrikAccount.getLevel(), true)
                .addField(language.getTranslation("command-stats-embed-field-2-name"), Emojis.getRegion(region).getFormatted() + " " + riotGamesAPI.getPlatformData(region).getRegionName(), true);
        if(rsoConnection.isExisting()) {
            embed.addField(language.getTranslation("command-stats-embed-field-3-name"),
                    Emojis.getDiscord().getFormatted() + " " + Bot.getJDA().getUserById(rsoConnection.getDiscordUserId()).getAsMention() + " (`" + Bot.getJDA().getUserById(rsoConnection.getDiscordUserId()).getAsTag() + "`)", true);
        }

        if(currentMmrData.getRankName() == null) {
            Rank rank = ranks.get(0);
            embed.setThumbnail(rank.getLargeIcon())
                    .addField(language.getTranslation("command-stats-embed-field-4-name"), Emojis.getRankByTierName(rank.getId()).getFormatted()  + " " + currentMmrData.getRankName(), false)
                    .addField(language.getTranslation("command-stats-embed-field-5-name"),
                            "**" + currentMmrData.getNeededGameCount() + "** " + (currentMmrData.getNeededGameCount() == 1 ? language.getTranslation("command-stats-embed-field-5-text-1") : language.getTranslation("command-stats-embed-field-5-text-2")), true);
        } else {
            for (Rank rank : ranks) {
                if (rank.getId() == currentMmrData.getRankId()) {
                    embed.addField(language.getTranslation("command-stats-embed-field-4-name"), Emojis.getRankByTierName(rank.getId()).getFormatted() + " " + rank.getName(), false)
                            .setThumbnail(rank.getLargeIcon());
                    if (rank.getId() >= 24) {
                        embed.addField(language.getTranslation("command-stats-embed-field-6-name"), "**" + currentMmrData.getRating() + "**RR » " +
                                (currentMmrData.getLastRatingChange() >= 0 ? Emojis.getIncrease().getFormatted() + " **+" + currentMmrData.getLastRatingChange() + "**" : Emojis.getDecrease().getFormatted() + " **" + currentMmrData.getLastRatingChange() + "**"), false);
                    } else {
                        embed.addField(language.getTranslation("command-stats-embed-field-6-name"), getProgressBar(currentMmrData.getRating()) + "\n" + "**" + currentMmrData.getRating() + "**/**100** » " +
                                (currentMmrData.getLastRatingChange() >= 0 ? Emojis.getIncrease().getFormatted() + " **+" + currentMmrData.getLastRatingChange() + "**" : Emojis.getDecrease().getFormatted() + " **" + currentMmrData.getLastRatingChange() + "**"), false);
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