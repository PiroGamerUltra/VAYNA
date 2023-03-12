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
import dev.piste.vayna.util.UnicodeEmoji;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

import java.io.IOException;
import java.util.Comparator;

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

        Queue queue = match.getMatchInfo().getQueue(language);

        Embed matchEmbed = new Embed()
                .setAuthor(riotAccount.getRiotId(), author.getIconUrl())
                .setImage(match.getMatchInfo().getMap(language).getListViewIcon())
                .addField(language.getTranslation("selectmenu-history-embed-field-1-name"), UnicodeEmoji.CALENDAR.getUnicode() + " <t:" + match.getMatchInfo().getGameStartDate().getTime() / 1000 + ":D>\n" +
                        UnicodeEmoji.CLOCK.getUnicode() + " <t:" + match.getMatchInfo().getGameStartDate().getTime() / 1000 + ":t> - <t:" + match.getMatchInfo().getGameEndDate().getTime() / 1000 + ":t> " +
                        "(" + (match.getMatchInfo().getGameEndDate().getTime() - match.getMatchInfo().getGameStartDate().getTime()) / 1000 / 60 + " minutes)", false)
                .addField(language.getTranslation("selectmenu-history-embed-field-2-name"), DiscordEmoji.Queue.getQueueById(queue.getId()).getAsDiscordEmoji().getFormatted() + " " + queue.getDropdownText(), false);

        if (queue.getName().equalsIgnoreCase("deathmatch")) {
            Match.Team playerTeam = match.getTeams().stream()
                    .filter(matchTeam -> matchTeam.getId().equalsIgnoreCase(player.getPUUID()))
                    .findFirst()
                    .orElse(null);
            Match.Team enemyTeam;
            int r = 255, g = 255;
            String title;
            if (playerTeam.isWinner()) {
                enemyTeam = match.getTeams().stream()
                        .filter(team -> !team.getId().equalsIgnoreCase(player.getPUUID()))
                        .max(Comparator.comparingInt(Match.Team::getPoints))
                        .orElse(null);
                r = 0;
                title = language.getTranslation("selectmenu-history-embed-title-victory");
            } else {
                enemyTeam = match.getTeams().stream()
                        .filter(team -> !team.getId().equalsIgnoreCase(player.getPUUID()))
                        .filter(Match.Team::isWinner)
                        .findFirst()
                        .orElse(null);
                g = 0;
                title = language.getTranslation("selectmenu-history-embed-title-defeat");
            }
            matchEmbed.setColor(r, g, 0)
                    .setTitle(language.getEmbedTitlePrefix() + title + " (" + playerTeam.getPoints() + " - " + enemyTeam.getPoints() + ")");
        } else {
            Match.Team ownTeam = match.getTeams().stream()
                    .filter(matchTeam -> matchTeam.getId().equalsIgnoreCase(player.getTeamId()))
                    .findFirst()
                    .orElse(null);

            Match.Team enemyTeam = match.getTeams().stream()
                    .filter(matchTeam -> !matchTeam.getId().equalsIgnoreCase(player.getTeamId()))
                    .findFirst()
                    .orElse(null);

            int r = 255, g = 255;
            String title = language.getEmbedTitlePrefix() + language.getTranslation("selectmenu-history-embed-title-draw");

            if (ownTeam.isWinner()) {
                r = 0;
                g = 255;
                title = language.getEmbedTitlePrefix() + language.getTranslation("selectmenu-history-embed-title-victory");
            } else if (enemyTeam.isWinner()) {
                r = 255;
                g = 0;
                title = language.getEmbedTitlePrefix() + language.getTranslation("selectmenu-history-embed-title-defeat");
            }

            matchEmbed.setColor(r, g, 0)
                    .setTitle(title + " (" + ownTeam.getPoints() + " - " + enemyTeam.getPoints() + ")");
        }

        event.getHook().editOriginalEmbeds(matchEmbed.build()).queue();
    }

    @Override
    public String getName() {
        return "history";
    }
}