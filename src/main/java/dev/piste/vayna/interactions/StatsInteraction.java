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
import dev.piste.vayna.interactions.buttons.HistoryButton;
import dev.piste.vayna.interactions.util.exceptions.InvalidRegionException;
import dev.piste.vayna.interactions.util.exceptions.RSOConnectionMissingException;
import dev.piste.vayna.interactions.util.exceptions.RSOConnectionPrivateException;
import dev.piste.vayna.mongodb.RSOConnection;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emojis;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.io.IOException;
import java.util.List;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class StatsInteraction {

    public static void sendStatsEmbed(RiotAccount riotAccount, RSOConnection rsoConnection, InteractionHook hook, Language language) throws IOException, HttpErrorException, InterruptedException, RSOConnectionMissingException, RSOConnectionPrivateException, InvalidRegionException {

        if(!rsoConnection.isExisting()) {
            if(rsoConnection.getDiscordUserId() != 0) {
                if (rsoConnection.getDiscordUserId() == hook.getInteraction().getUser().getIdLong()) {
                    throw new RSOConnectionMissingException(hook.getInteraction().getUser());
                } else {
                    throw new RSOConnectionMissingException(Bot.getJDA().getUserById(rsoConnection.getDiscordUserId()));
                }
            }
        } else {
            if(!rsoConnection.isPubliclyVisible() && (rsoConnection.getDiscordUserId() != hook.getInteraction().getUser().getIdLong())) {
                throw new RSOConnectionPrivateException(Bot.getJDA().getUserById(rsoConnection.getDiscordUserId()));
            }
        }

        RiotGamesAPI riotGamesAPI = new RiotGamesAPI();
        HenrikAPI henrikAPI = new HenrikAPI();

        String region;
        try {
            region = riotGamesAPI.getRegion(riotAccount.getPUUID());
        } catch (HttpErrorException e) {
            if(e.getStatusCode() == 400 || e.getStatusCode() == 404) {
                throw new InvalidRegionException(riotAccount.getName(), riotAccount.getTag());
            } else {
                throw e;
            }
        }
        HenrikAccount henrikAccount = henrikAPI.getAccount(riotAccount.getName(), riotAccount.getTag());
        MMR.CurrentData currentMmrData = henrikAPI.getMMR(henrikAccount.getId(), henrikAccount.getRegion()).getCurrentData();
        List<Rank> ranks = new OfficerAPI().getRanks(language.getLocale());

        Embed embed = new Embed()
                .setAuthor(riotAccount.getRiotId(), henrikAccount.getPlayerCard() != null ? henrikAccount.getPlayerCard().getSmallArt() : null)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("stats-embed-title"))
                .setDescription(language.getTranslation("stats-embed-desc"));
        if(rsoConnection.isExisting()) {
            User connectionUser = Bot.getJDA().getUserById(rsoConnection.getDiscordUserId());
            embed.addField(language.getTranslation("stats-embed-field-1-name"),
                    Emojis.getDiscord().getFormatted() + " `" + connectionUser.getAsTag() + "` (" + connectionUser.getAsMention() + ")", false);
        }
        embed.addField(language.getTranslation("stats-embed-field-2-name"), Emojis.getLevel().getFormatted() + " " + henrikAccount.getLevel(), true)
                .addField(language.getTranslation("stats-embed-field-3-name"), Emojis.getRegion(region).getFormatted() + " " + riotGamesAPI.getPlatformData(region).getRegionName(), true);


        if(currentMmrData.getRankName() == null) {
            Rank rank = ranks.get(0);
            embed.setThumbnail(rank.getLargeIcon())
                    .addField(language.getTranslation("stats-embed-field-4-name"), Emojis.getRankByTierName(rank.getId()).getFormatted()  + " " + currentMmrData.getRankName(), false);
        } else {
            for (Rank rank : ranks) {
                if (rank.getId() == currentMmrData.getRankId()) {
                    embed.addField(language.getTranslation("stats-embed-field-4-name"), Emojis.getRankByTierName(rank.getId()).getFormatted() + " " + rank.getName(), false)
                            .setThumbnail(rank.getLargeIcon());
                    if (rank.getId() >= 24) {
                        embed.addField(language.getTranslation("stats-embed-field-5-name"), "**" + currentMmrData.getRating() + "**RR » " +
                                (currentMmrData.getLastRatingChange() >= 0 ? Emojis.getIncrease().getFormatted() + " **+" + currentMmrData.getLastRatingChange() + "**" : Emojis.getDecrease().getFormatted() + " **" + currentMmrData.getLastRatingChange() + "**"), false);
                    } else {
                        embed.addField(language.getTranslation("stats-embed-field-5-name"), getProgressBar(currentMmrData.getRating()) + "\n" + "**" + currentMmrData.getRating() + "**/**100** » " +
                                (currentMmrData.getLastRatingChange() >= 0 ? Emojis.getIncrease().getFormatted() + " **+" + currentMmrData.getLastRatingChange() + "**" : Emojis.getDecrease().getFormatted() + " **" + currentMmrData.getLastRatingChange() + "**"), false);
                    }
                }
            }
        }

        hook.editOriginalEmbeds(embed.build()).setActionRow(
                Button.secondary(new HistoryButton().getName() + ";" + riotAccount.getPUUID(), language.getTranslation("button-history-name"))
        ).queue();
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