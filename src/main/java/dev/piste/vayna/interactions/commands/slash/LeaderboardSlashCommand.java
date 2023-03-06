package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.henrik.HenrikAPI;
import dev.piste.vayna.apis.henrik.gson.HenrikAccount;
import dev.piste.vayna.apis.henrik.gson.MMR;
import dev.piste.vayna.apis.officer.OfficerAPI;
import dev.piste.vayna.apis.officer.gson.competitivetier.Tier;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.mongodb.RsoConnection;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.translations.LanguageManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emojis;
import dev.piste.vayna.util.templates.Buttons;
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
    public void perform(SlashCommandInteractionEvent event, Language language) throws HttpErrorException, IOException, InterruptedException {
        event.deferReply().queue();

        RiotAPI riotAPI = new RiotAPI();
        HenrikAPI henrikAPI = new HenrikAPI();

        // Put all linked accounts in this guild in the eloMap
        HashMap<User, MMR> mmrMap = new HashMap<>();
        int guildElo = 0;
        // Collecting every connected Riot Games account in this server
        for(Member member : event.getGuild().getMembers()) {
            RsoConnection rsoConnection = new RsoConnection(member.getUser().getIdLong());
            if(rsoConnection.isExisting() && rsoConnection.isPubliclyVisible()) {
                try {
                    RiotAccount riotAccount = riotAPI.getAccount(rsoConnection.getRiotPuuid());
                    HenrikAccount henrikAccount = henrikAPI.getAccount(riotAccount.getGameName(), riotAccount.getTagLine());
                    MMR mmr = henrikAPI.getMmr(henrikAccount.getPuuid(), henrikAccount.getRegion());
                    if(mmr.getRank().getCurrentTier() == 0) continue;
                    mmrMap.put(member.getUser(), mmr);
                    guildElo += mmr.getRank().getElo();
                } catch (HttpErrorException e) {
                    if(e.getStatusCode() == 429) throw e;
                }
            }
        }

        // If no one has connected his Riot Games account yet
        if(mmrMap.isEmpty()) {
            Embed embed = new Embed().setAuthor(event.getUser().getName(), event.getUser().getAvatarUrl())
                    .setColor(255, 0, 0)
                    .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-leaderboard-error-empty-embed-title"))
                    .setDescription(language.getTranslation("command-leaderboard-error-empty-embed-description"));
            event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                    Buttons.getSupportButton(language)
            ).queue();
            return;
        }

        Map<User, Integer> eloMap = new HashMap<>();
        for(Map.Entry<User, MMR> entry : mmrMap.entrySet()) {
            eloMap.put(entry.getKey(), entry.getValue().getRank().getElo());
        }

        // Create a list of the elos and sort it // Calculate the average elo of this guild
        List<Map.Entry<User, Integer>> eloEntryList = new ArrayList<>(eloMap.entrySet());
        eloEntryList.sort(Map.Entry.comparingByValue());
        Collections.reverse(eloEntryList);

        int averageGuildElo = guildElo / eloMap.size();

        Embed embed = new Embed()
                .setAuthor(event.getGuild().getName(), event.getGuild().getIconUrl())
                .setDescription(language.getTranslation("command-leaderboard-embed-description"));

        ArrayList<Tier> tierList = new OfficerAPI().getCompetitiveTier(language.getLanguageCode()).getTiers();

        // Create an embed field for the best 20 players in this guild
        for(int i = 0; i < 20; i++) {
            if(eloEntryList.size() == i) break;
            User user = eloEntryList.get(i).getKey();
            MMR mmr = mmrMap.get(user);
            Tier tier = tierList.stream()
                    .filter(forTier -> forTier.getTier() == mmr.getRank().getCurrentTier())
                    .findFirst()
                    .orElse(null);

            if(mmr.getRank().getElo() >= 2100) {
                embed.addField((i+1) + ". " + user.getAsTag() + " (" + Emojis.getRiotGames().getFormatted() + " " + mmr.getGameName() + "#" + mmr.getTagLine() + ")",
                        Emojis.getRankByTierName(mmr.getRank().getCurrentTier()).getFormatted() + " " + tier.getTierName() +
                                " (**" + mmr.getRank().getRankingInTier() + "RR**)", false);
            } else {
                embed.addField((i+1) + ". " + user.getAsTag() + " (" + Emojis.getRiotGames().getFormatted() + " " + mmr.getGameName() + "#" + mmr.getTagLine() + ")",
                        Emojis.getRankByTierName(mmr.getRank().getCurrentTier()).getFormatted() + " " + tier.getTierName() +
                                " (**" + mmr.getRank().getRankingInTier() + "**/**100**)", false);
            }
        }

        // Put the average guild elo in the embed
        int guildRatingInTier;
        Tier guildTier;

        if(averageGuildElo < 2100) {
            guildRatingInTier = averageGuildElo % 100;
            guildTier = tierList.get((int) ((averageGuildElo / 100.0) + 3.0));
        } else {
            guildRatingInTier = averageGuildElo - 2100;
            if(guildRatingInTier < 90) {
                guildTier = tierList.get(24);
            } else if(guildRatingInTier < 200) {
                guildTier = tierList.get(25);
            } else if(guildRatingInTier < 450) {
                guildTier = tierList.get(26);
            } else {
                guildTier = tierList.get(27);
            }
        }
        embed.setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-leaderboard-embed-title")
                .replaceAll("%rank:name%", guildTier.getTierName())
                .replaceAll("%rank:rating%", String.valueOf(guildRatingInTier)));
        embed.setThumbnail(guildTier.getLargeIcon());

        // Reply
        event.getHook().editOriginalEmbeds(embed.build()).queue();
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(getName(), getDescription()).setGuildOnly(true);
    }

    @Override
    public String getName() {
        return "leaderboard";
    }

    @Override
    public String getDescription() {
        return LanguageManager.getLanguage().getTranslation("command-leaderboard-description");
    }
}
