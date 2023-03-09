package dev.piste.vayna.interactions.selectmenus.string;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.RiotGamesAPI;
import dev.piste.vayna.apis.entities.officer.Queue;
import dev.piste.vayna.apis.entities.riotgames.Match;
import dev.piste.vayna.apis.entities.riotgames.RiotAccount;
import dev.piste.vayna.interactions.util.interfaces.IStringSelectMenu;
import dev.piste.vayna.mongodb.MongoMatch;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.util.Embed;
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
        String region = event.getValues().get(0).split(";")[1];

        RiotAccount riotAccount = riotGamesAPI.getAccount(author.getName().split("#")[0], author.getName().split("#")[1]);
        MongoMatch mongoMatch = new MongoMatch(matchId);
        Match match;
        if(mongoMatch.isExisting()) {
            match = mongoMatch.getMatch();
        } else {
            match = new MongoMatch(riotGamesAPI.getMatch(matchId, region)).insert().getMatch();
        }
        Match.Player player = match.getPlayers().stream()
                .filter(matchPlayer -> matchPlayer.getPUUID().equalsIgnoreCase(riotAccount.getPUUID()))
                .findFirst()
                .orElse(null);

        Queue queue = match.getMatchInfo().getQueue(language.getLocale());

        Embed matchEmbed = new Embed()
                .setAuthor(riotAccount.getRiotId(), author.getIconUrl())
                .setTitle(language.getEmbedTitlePrefix() + queue.getDropdownText())
                .setImage(match.getMatchInfo().getMap(language.getLocale()).getListViewIcon())
                .addField("Date", "\uD83D\uDCC5 <t:" + match.getMatchInfo().getGameStartDate().getTime() / 1000 + ":D>\n" +
                        "\uD83D\uDD50 <t:" + match.getMatchInfo().getGameStartDate().getTime() / 1000 + ":t> - <t:" + match.getMatchInfo().getGameEndDate().getTime() / 1000 + ":t> " +
                        "(" + (match.getMatchInfo().getGameEndDate().getTime() - match.getMatchInfo().getGameStartDate().getTime()) / 1000 / 60 + " minutes)", false);
        if(queue.getName().equalsIgnoreCase("competitive")) {
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
                        .setTitle(language.getEmbedTitlePrefix() + queue.getDropdownText() + " (" + "Victory " + ownTeam.getWonRoundsCount() + " - " + enemyTeam.getWonRoundsCount() + ")");
            } else {
                if(enemyTeam.isWinner()) {
                    matchEmbed.setColor(255, 0, 0)
                            .setTitle(language.getEmbedTitlePrefix() + queue.getDropdownText() + " (" + "Defeat " + ownTeam.getWonRoundsCount() + " - " + enemyTeam.getWonRoundsCount() + ")");
                } else {
                    matchEmbed.setColor(255, 255, 0)
                            .setTitle(language.getEmbedTitlePrefix() + queue.getDropdownText() + " (" + "Draw " + ownTeam.getWonRoundsCount() + " - " + enemyTeam.getWonRoundsCount() + ")");
                }
            }
        }

        event.getHook().editOriginalEmbeds(matchEmbed.build()).queue();
    }

    @Override
    public String getName() {
        return "history";
    }
}