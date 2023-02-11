package dev.piste.vayna.commands;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.henrik.HenrikAPI;
import dev.piste.vayna.apis.henrik.gson.HenrikAccount;
import dev.piste.vayna.apis.henrik.gson.MMR;
import dev.piste.vayna.apis.henrik.gson.mmr.Rank;
import dev.piste.vayna.apis.riotgames.RiotAPI;
import dev.piste.vayna.apis.riotgames.gson.RiotAccount;
import dev.piste.vayna.apis.valorantapi.ValorantAPI;
import dev.piste.vayna.apis.valorantapi.gson.competitivetier.Tier;
import dev.piste.vayna.manager.Command;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import dev.piste.vayna.util.buttons.Buttons;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author Piste | https://github.com/zPiste
 */
public class LeaderboardCommand implements Command {

    @Override
    public void perform(SlashCommandInteractionEvent event) throws StatusCodeException {
        event.deferReply().queue();
        Language language = LanguageManager.getLanguage(event.getGuild());

        // Put all linked accounts in this guild in the eloMap
        HashMap<User, MMR> eloMap = new HashMap<>();

        // Collecting every connected Riot Games account in this server
        for(Member member : event.getGuild().getMembers()) {
            LinkedAccount linkedAccount = new LinkedAccount(member.getUser().getIdLong());
            if(linkedAccount.isExisting() && linkedAccount.isVisibleToPublic()) {
                try {
                    RiotAccount riotAccount = RiotAPI.getAccountByPuuid(linkedAccount.getRiotPuuid());
                    HenrikAccount henrikAccount = HenrikAPI.getAccountByRiotId(riotAccount.getGameName(), riotAccount.getTagLine());
                    eloMap.put(member.getUser(), henrikAccount.getMmr());
                } catch (StatusCodeException e) {
                    if(e.getMessage().startsWith("429")) throw new StatusCodeException(e.getMessage());
                }
            }
        }

        // If no one has connected his Riot Games account yet
        if(eloMap.isEmpty()) {
            Embed embed = new Embed().setAuthor(event.getUser().getName(), event.getUser().getAvatarUrl())
                    .setColor(255, 0, 0)
                    .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-leaderboard-error-empty-embed-title"))
                    .setDescription(language.getTranslation("command-leaderboard-error-empty-embed-description"));
            event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                    Buttons.getSupportButton(event.getGuild())
            ).queue();
            return;
        }

        // Create a list of the elos and sort it // Calculate the average elo of this guild
        ArrayList<Integer> eloList = new ArrayList<>();
        int memberElos = 0;
        for(MMR mmr : eloMap.values()) {
            memberElos += mmr.getRank().getElo();
            eloList.add(mmr.getRank().getElo());
        }
        eloList.sort(Collections.reverseOrder());
        int guildElo = memberElos / eloMap.size();

        Embed embed = new Embed()
                .setAuthor(event.getGuild().getName(), event.getGuild().getIconUrl())
                .setDescription(language.getTranslation("command-leaderboard-embed-description"));

        ArrayList<Tier> tierList = ValorantAPI.getCompetitiveTier(language.getLanguageCode()).getTiers();

        // Create an embed field for the best 20 players in this guild
        for(int i = 0; i<20; i++) {
            if(eloList.size() == i) break;
            for(HashMap.Entry<User, MMR> entry : eloMap.entrySet()) {
                User user = entry.getKey();
                MMR mmr = entry.getValue();
                Rank rank = mmr.getRank();
                Tier tier = null;
                for(Tier forTier : tierList) {
                    if(forTier.getTier() == rank.getCurrentTier()) {
                        tier = forTier;
                        break;
                    }
                }
                if(entry.getValue().getRank().getElo() == eloList.get(i)) {
                    if(rank.getElo() >= 2100) {
                        embed.addField((i+1) + ". " + user.getAsTag() + " (" + Emoji.getRiotGames().getFormatted() + " " + mmr.getGameName() + "#" + mmr.getTagLine() + ")",
                                Emoji.getRankByTierName(rank.getCurrentTier()).getFormatted() + " " + tier.getTierName() +
                                        " (**" + rank.getRankingInTier() + "RR**)", false);
                    } else {
                        embed.addField((i+1) + ". " + user.getAsTag() + " (" + Emoji.getRiotGames().getFormatted() + " " + mmr.getGameName() + "#" + mmr.getTagLine() + ")",
                                Emoji.getRankByTierName(rank.getCurrentTier()).getFormatted() + " " + tier.getTierName() +
                                        " (**" + rank.getRankingInTier() + "**/**100**)", false);
                    }

                }
            }
        }

        // Put the average guild elo in the embed
        int guildRatingInTier;
        Tier guildTier;

        if(guildElo < 2100) {
            guildRatingInTier = guildElo % 100;
            guildTier = tierList.get((int) ((guildElo / 100.0) + 3.0));
        } else {
            guildRatingInTier = guildElo - 2100;
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
