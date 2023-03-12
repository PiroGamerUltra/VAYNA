package dev.piste.vayna.interactions.general;

import dev.piste.vayna.Bot;
import dev.piste.vayna.http.HttpErrorException;
import dev.piste.vayna.http.apis.HenrikAPI;
import dev.piste.vayna.http.apis.OfficerAPI;
import dev.piste.vayna.http.apis.RiotGamesAPI;
import dev.piste.vayna.http.models.henrik.HenrikAccount;
import dev.piste.vayna.http.models.henrik.MMR;
import dev.piste.vayna.http.models.officer.Rank;
import dev.piste.vayna.http.models.riotgames.RiotAccount;
import dev.piste.vayna.interactions.buttons.HistoryButton;
import dev.piste.vayna.interactions.util.exceptions.InvalidRegionException;
import dev.piste.vayna.interactions.util.exceptions.RSOConnectionMissingException;
import dev.piste.vayna.interactions.util.exceptions.RSOConnectionPrivateException;
import dev.piste.vayna.mongodb.RSOConnection;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.util.DiscordEmoji;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.RiotRegion;
import dev.piste.vayna.util.UnicodeEmoji;
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

        String regionId;
        try {
            regionId = riotGamesAPI.getRegion(riotAccount.getPUUID());
        } catch (HttpErrorException e) {
            if(e.getStatusCode() == 400 || e.getStatusCode() == 404) {
                throw new InvalidRegionException(riotAccount.getName(), riotAccount.getTag());
            } else {
                throw e;
            }
        }
        RiotRegion region = RiotRegion.getRiotRegionById(regionId);
        HenrikAccount henrikAccount = henrikAPI.getAccount(riotAccount.getName(), riotAccount.getTag());
        MMR.CurrentData currentMmrData = henrikAPI.getMMR(henrikAccount.getId(), henrikAccount.getRegion()).getCurrentData();
        List<Rank> ranks = new OfficerAPI().getRanks(language.getLocale());

        Embed embed = new Embed()
                .setAuthor(riotAccount.getRiotId(), henrikAccount.getPlayerCard() != null ? henrikAccount.getPlayerCard().getSmallArt() : null)
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("stats-embed-title"))
                .setDescription(language.getTranslation("stats-embed-desc"));
        if(rsoConnection.isExisting()) {
            User connectionUser = hook.getJDA().getUserById(rsoConnection.getDiscordUserId());
            if(connectionUser != null) {
                embed.addField(language.getTranslation("stats-embed-field-1-name"),
                        DiscordEmoji.DISCORD.getAsDiscordEmoji().getFormatted() + " `" + connectionUser.getAsTag() + "` (" + connectionUser.getAsMention() + ")", false);
            }
        }
        embed.addField(language.getTranslation("stats-embed-field-2-name"), DiscordEmoji.LEVEL.getAsDiscordEmoji().getFormatted() + " " + henrikAccount.getLevel(), true)
                .addField(language.getTranslation("stats-embed-field-3-name"), UnicodeEmoji.Region.getRegionById(region.getId()).getAsDiscordEmoji().getFormatted() + " " + region.getName(), true);


        if(currentMmrData.getRankName() == null) {
            Rank rank = ranks.get(0);
            embed.setThumbnail(rank.getLargeIcon())
                    .addField(language.getTranslation("stats-embed-field-4-name"), DiscordEmoji.Rank.getRankById(rank.getId()).getAsDiscordEmoji().getFormatted()  + " " + rank.getName(), false);
        } else {
            for (Rank rank : ranks) {
                if (rank.getId() == currentMmrData.getRankId()) {
                    embed.addField(language.getTranslation("stats-embed-field-4-name"), DiscordEmoji.Rank.getRankById(rank.getId()).getAsDiscordEmoji().getFormatted() + " " + rank.getName(), false)
                            .setThumbnail(rank.getLargeIcon());
                    if (rank.getId() >= 24) {
                        embed.addField(language.getTranslation("stats-embed-field-5-name"), "**" + currentMmrData.getRating() + "**RR » " +
                                (currentMmrData.getLastRatingChange() >= 0 ? DiscordEmoji.INCREASE.getAsDiscordEmoji().getFormatted() + " **+" + currentMmrData.getLastRatingChange() + "**" : DiscordEmoji.DECREASE.getAsDiscordEmoji().getFormatted() + " **" + currentMmrData.getLastRatingChange() + "**"), false);
                    } else {
                        embed.addField(language.getTranslation("stats-embed-field-5-name"), getProgressBar(currentMmrData.getRating()) + "\n" + "**" + currentMmrData.getRating() + "**/**100** » " +
                                (currentMmrData.getLastRatingChange() >= 0 ? DiscordEmoji.INCREASE.getAsDiscordEmoji().getFormatted() + " **+" + currentMmrData.getLastRatingChange() + "**" : DiscordEmoji.DECREASE.getAsDiscordEmoji().getFormatted() + " **" + currentMmrData.getLastRatingChange() + "**"), false);
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
        String greenStart = DiscordEmoji.PROGRESS_BAR_GREEN_START.getAsDiscordEmoji().getFormatted();
        String greenLine = DiscordEmoji.PROGRESS_BAR_GREEN_LINE.getAsDiscordEmoji().getFormatted();
        String greenEnd = DiscordEmoji.PROGRESS_BAR_GREEN_END.getAsDiscordEmoji().getFormatted();
        String redStart = DiscordEmoji.PROGRESS_BAR_RED_START.getAsDiscordEmoji().getFormatted();
        String redLine = DiscordEmoji.PROGRESS_BAR_RED_LINE.getAsDiscordEmoji().getFormatted();
        String redEnd = DiscordEmoji.PROGRESS_BAR_RED_END.getAsDiscordEmoji().getFormatted();
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