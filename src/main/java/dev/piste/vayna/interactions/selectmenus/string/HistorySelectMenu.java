package dev.piste.vayna.interactions.selectmenus.string;

import dev.piste.vayna.http.HttpErrorException;
import dev.piste.vayna.http.apis.RiotGamesAPI;
import dev.piste.vayna.http.apis.ValorantAPI;
import dev.piste.vayna.http.models.officer.Queue;
import dev.piste.vayna.http.models.riotgames.Match;
import dev.piste.vayna.http.models.riotgames.RiotAccount;
import dev.piste.vayna.interactions.util.interfaces.IStringSelectMenu;
import dev.piste.vayna.mongodb.MongoMatch;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.util.DiscordEmoji;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.RiotRegion;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class HistorySelectMenu implements IStringSelectMenu {

    @Override
    public void perform(StringSelectInteractionEvent event, Language language) throws HttpErrorException, IOException, InterruptedException {
        MessageEmbed.AuthorInfo author = event.getMessage().getEmbeds().get(0).getAuthor();
        event.deferEdit().queue();

        RiotGamesAPI riotGamesAPI = new RiotGamesAPI();

        String matchId = event.getValues().get(0).split(";")[0];
        String regionId = event.getValues().get(0).split(";")[1];

        ValorantAPI valorantAPI = new ValorantAPI(RiotRegion.getRiotRegionById(regionId));

        RiotAccount riotAccount = riotGamesAPI.getAccount(author.getName().split("#")[0], author.getName().split("#")[1]);
        MongoMatch mongoMatch = new MongoMatch(matchId);
        Match match;
        if(mongoMatch.isExisting()) {
            match = mongoMatch.getMatch();
        } else {
            match = new MongoMatch(valorantAPI.getMatch(matchId)).insert().getMatch();
        }
        Match.Player player = match.getPlayers().stream()
                .filter(matchPlayer -> matchPlayer.getPUUID().equalsIgnoreCase(riotAccount.getPUUID()))
                .findFirst()
                .orElse(null);

        Queue queue = match.getMatchInfo().getQueue(language.getLocale());

        Embed matchEmbed = new Embed()
                .setAuthor(riotAccount.getRiotId(), author.getIconUrl())
                .setImage(match.getMatchInfo().getMap(language.getLocale()).getListViewIcon())
                .addField("Date", "\uD83D\uDCC5 <t:" + match.getMatchInfo().getGameStartDate().getTime() / 1000 + ":D>\n" +
                        "\uD83D\uDD50 <t:" + match.getMatchInfo().getGameStartDate().getTime() / 1000 + ":t> - <t:" + match.getMatchInfo().getGameEndDate().getTime() / 1000 + ":t> " +
                        "(" + (match.getMatchInfo().getGameEndDate().getTime() - match.getMatchInfo().getGameStartDate().getTime()) / 1000 / 60 + " minutes)", false)
                .addField("Game Mode", DiscordEmoji.Queue.getQueueById(queue.getId()).getAsDiscordEmoji().getFormatted() + " " + queue.getDropdownText(), false);

        if(!queue.getName().equalsIgnoreCase("deathmatch")) {
            Match.Team ownTeam = match.getTeams().stream()
                    .filter(matchTeam -> matchTeam.getId().equalsIgnoreCase(player.getTeamId()))
                    .findFirst()
                    .orElse(null);
            Match.Team enemyTeam = match.getTeams().stream()
                    .filter(matchTeam -> !matchTeam.getId().equalsIgnoreCase(player.getTeamId()))
                    .findFirst()
                    .orElse(null);
            if(ownTeam.isWinner()) {
                matchEmbed.setColor(0, 255, 0)
                        .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("selectmenu-history-embed-title-victory") + " (" + ownTeam.getWonRoundsCount() + " - " + enemyTeam.getWonRoundsCount() + ")");
            } else {
                if(enemyTeam.isWinner()) {
                    matchEmbed.setColor(255, 0, 0)
                            .setTitle(language.getEmbedTitlePrefix() + queue.getDropdownText() + language.getTranslation("selectmenu-history-embed-title-defeat") + " (" + ownTeam.getWonRoundsCount() + " - " + enemyTeam.getWonRoundsCount() + ")");
                } else {
                    matchEmbed.setColor(255, 255, 0)
                            .setTitle(language.getEmbedTitlePrefix() + queue.getDropdownText() + language.getTranslation("selectmenu-history-embed-title-draw") + " (" + ownTeam.getWonRoundsCount() + " - " + enemyTeam.getWonRoundsCount() + ")");
                }
            }
        } else {
            Match.Team playerTeam = match.getTeams().stream()
                    .filter(matchTeam -> matchTeam.getId().equalsIgnoreCase(player.getPUUID()))
                    .findFirst()
                    .orElse(null);

            if(playerTeam.isWinner()) {
                Match.Team secondPlaceTeam = null;
                for(Match.Team team : match.getTeams()) {
                    if(team.getId().equalsIgnoreCase(player.getPUUID())) continue;
                    if(secondPlaceTeam == null) {
                        secondPlaceTeam = team;
                    } else {
                        if(team.getPoints() > secondPlaceTeam.getPoints()) {
                            secondPlaceTeam = team;
                        }
                    }
                }
                matchEmbed.setColor(0, 255, 0)
                        .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("selectmenu-history-embed-title-victory") + " (" + playerTeam.getPoints() + " - " + secondPlaceTeam.getPoints() + ")");
            } else  {
                Match.Team winnerTeam = null;
                for(Match.Team team : match.getTeams()) {
                    if(team.getId().equalsIgnoreCase(player.getPUUID())) continue;
                    if(team.isWinner()) {
                        winnerTeam = team;
                        break;
                    }
                }
                matchEmbed.setColor(255, 0, 0)
                        .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("selectmenu-history-embed-title-defeat") + " (" + playerTeam.getPoints() + " - " + winnerTeam.getPoints() + ")");
            }
        }

        event.getHook().editOriginalEmbeds(matchEmbed.build()).queue();
    }

    @Override
    public String getName() {
        return "history";
    }
}