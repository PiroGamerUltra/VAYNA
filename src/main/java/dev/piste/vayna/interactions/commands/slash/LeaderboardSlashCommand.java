package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.apis.HenrikAPI;
import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.OfficerAPI;
import dev.piste.vayna.apis.RiotGamesAPI;
import dev.piste.vayna.apis.entities.henrik.HenrikAccount;
import dev.piste.vayna.apis.entities.henrik.MMR;
import dev.piste.vayna.apis.entities.officer.Rank;
import dev.piste.vayna.apis.entities.officer.Season;
import dev.piste.vayna.apis.entities.riotgames.Leaderboard;
import dev.piste.vayna.apis.entities.riotgames.RiotAccount;
import dev.piste.vayna.interactions.util.exceptions.GuildConnectionsMissingException;
import dev.piste.vayna.interactions.util.interfaces.ISlashCommand;
import dev.piste.vayna.mongodb.RSOConnection;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.translations.LanguageManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emojis;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.io.IOException;
import java.util.*;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class LeaderboardSlashCommand implements ISlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event, Language language) throws HttpErrorException, IOException, InterruptedException, GuildConnectionsMissingException {
        event.deferReply(false).queue();

        RiotGamesAPI riotGamesAPI = new RiotGamesAPI();
        HenrikAPI henrikAPI = new HenrikAPI();
        OfficerAPI officerAPI = new OfficerAPI();

        // Put all linked accounts in this guild in the eloMap
        HashMap<User, MMR> mmrMap = new HashMap<>();
        int guildMemberElo = 0;
        // Collecting every connected Riot Games account in this server
        for(Member member : event.getGuild().getMembers()) {
            RSOConnection rsoConnection = new RSOConnection(member.getUser().getIdLong());
            if(rsoConnection.isExisting() && rsoConnection.isPubliclyVisible()) {
                try {
                    RiotAccount riotAccount = riotGamesAPI.getAccount(rsoConnection.getRiotPuuid());
                    HenrikAccount henrikAccount = henrikAPI.getAccount(riotAccount.getName(), riotAccount.getTag());
                    MMR mmr = henrikAPI.getMMR(henrikAccount.getId(), henrikAccount.getRegion());
                    if(mmr.getCurrentData().getRankId() == 0) continue;
                    mmrMap.put(member.getUser(), mmr);
                    guildMemberElo += mmr.getCurrentData().getElo();
                } catch (HttpErrorException e) {
                    if(e.getStatusCode() == 429) throw e;
                }
            }
        }

        // If no one has connected his Riot Games account yet
        if(mmrMap.isEmpty()) throw new GuildConnectionsMissingException(event.getGuild());

        Map<User, Integer> eloMap = new HashMap<>();
        for(Map.Entry<User, MMR> entry : mmrMap.entrySet()) {
            eloMap.put(entry.getKey(), entry.getValue().getCurrentData().getElo());
        }

        // Create a list of the elos and sort it // Calculate the average elo of this guild
        List<Map.Entry<User, Integer>> eloEntryList = new ArrayList<>(eloMap.entrySet());
        eloEntryList.sort(Map.Entry.comparingByValue());
        Collections.reverse(eloEntryList);

        Embed embed = new Embed()
                .setAuthor(event.getGuild().getName(), event.getGuild().getIconUrl())
                .setDescription(language.getTranslation("command-leaderboard-embed-desc"));

        List<Rank> ranks = officerAPI.getRanks(language.getLocale());

        // Create an embed field for the best 20 players in this guild
        for(int i = 0; i < 20; i++) {
            if(eloEntryList.size() == i) break;
            User user = eloEntryList.get(i).getKey();
            MMR mmr = mmrMap.get(user);
            Rank rank = ranks.stream()
                    .filter(foundRank -> foundRank.getId() == mmr.getCurrentData().getRankId())
                    .findFirst()
                    .orElse(null);

            if(mmr.getCurrentData().getElo() >= 2100) {
                embed.addField((i+1) + ". " + user.getAsTag() + " (" + Emojis.getRiotGames().getFormatted() + " " + mmr.getName() + "#" + mmr.getTag() + ")",
                        Emojis.getRankByTierName(mmr.getCurrentData().getRankId()).getFormatted() + " " + rank.getName() +
                                " (**" + mmr.getCurrentData().getRating() + "RR**)", false);
            } else {
                embed.addField((i+1) + ". " + user.getAsTag() + " (" + Emojis.getRiotGames().getFormatted() + " " + mmr.getName() + "#" + mmr.getTag() + ")",
                        Emojis.getRankByTierName(mmr.getCurrentData().getRankId()).getFormatted() + " " + rank.getName() +
                                " (**" + mmr.getCurrentData().getRating() + "**/**100**)", false);
            }
        }

        Season currentSeason = null;
        for(Season season : officerAPI.getSeasons(language.getLocale())) {
            if(season.getParentSeason(language.getLocale()) == null) continue;
            if(season.getStartDate().before(new Date()) && season.getEndDate().after(new Date())) {
                currentSeason = season;
                break;
            }
        }
        if(currentSeason == null) throw new NullPointerException("No current season found");
        Leaderboard.TierDetails tierDetails = riotGamesAPI.getLeaderboard(currentSeason.getId(), 1, 0, "eu").getTierDetails();

        // Put the average guild elo in the embed
        int guildElo = guildMemberElo / eloMap.size();
        int guildRankId;
        int guildRating;

        if(guildElo < 2100) {
            guildRating = guildElo % 100;
            guildRankId = (int) ((guildElo / 100.0) + 3.0);
        } else {
            guildRating = guildElo - 2100;
            if(guildRating < tierDetails.getImmortal2().getRatingThreshold()) {
                guildRankId = 24;
            } else if(guildRating < tierDetails.getImmortal3().getRatingThreshold()) {
                guildRankId = 25;
            } else if(guildRating < tierDetails.getRadiant().getRatingThreshold()) {
                guildRankId = 26;
            } else {
                guildRankId = 27;
            }
        }
        Rank guildRank = ranks.get(guildRankId);
        embed.setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-leaderboard-embed-title")
                .replaceAll("%rank:name%", guildRank.getName())
                .replaceAll("%rank:rating%", String.valueOf(guildRating)));
        embed.setThumbnail(guildRank.getLargeIcon());

        event.getHook().editOriginalEmbeds(embed.build()).queue();
    }

    @Override
    public String getName() {
        return "leaderboard";
    }

    @Override
    public String getDescription(Language language) {
        return language.getTranslation("command-leaderboard-desc");
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(getName(), getDescription(LanguageManager.getDefaultLanguage())).setGuildOnly(true);
    }

}
