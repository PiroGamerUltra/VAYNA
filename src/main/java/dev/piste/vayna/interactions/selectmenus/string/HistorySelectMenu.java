package dev.piste.vayna.interactions.selectmenus.string;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.RiotGamesAPI;
import dev.piste.vayna.apis.entities.riotgames.Match;
import dev.piste.vayna.apis.entities.riotgames.RiotAccount;
import dev.piste.vayna.mongodb.MongoMatch;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emojis;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

        List<Match.Player> teamRed = match.getPlayers().stream()
                .filter(matchPlayer -> matchPlayer.getTeamId().equalsIgnoreCase("Red"))
                .toList();
        List<Match.Player> teamBlue = match.getPlayers().stream()
                .filter(matchPlayer -> matchPlayer.getTeamId().equalsIgnoreCase("Blue"))
                .toList();
        String redString = teamRed.stream()
                .map(matchPlayer -> Emojis.getRankByTierName(matchPlayer.getRankId()).getFormatted() + " " + matchPlayer.getRiotId())
                .collect(Collectors.joining("\n"));
        String blueString = teamBlue.stream()
                .map(matchPlayer -> Emojis.getRankByTierName(matchPlayer.getRankId()).getFormatted() + " " + matchPlayer.getRiotId())
                .collect(Collectors.joining("\n"));



        Embed embed = new Embed()
                .setAuthor(author.getName(), author.getIconUrl())
                .setImage(match.getMatchInfo().getMap(language.getLanguageCode()).getListViewIcon())
                .setThumbnail(player.getAgent(language.getLanguageCode()).getDisplayIcon())
                .setTitle(language.getEmbedTitlePrefix() + match.getMatchInfo().getQueue(language.getLanguageCode()).getDropdownText())
                .addField("Date", "\uD83D\uDCC5 <t:" + match.getMatchInfo().getGameStartDate().getTime() / 1000 + ":D>\n" +
                        "\uD83D\uDD50 <t:" + match.getMatchInfo().getGameStartDate().getTime() / 1000 + ":t> - <t:" + match.getMatchInfo().getGameEndDate().getTime() / 1000 + ":t> " +
                        "(" + (match.getMatchInfo().getGameEndDate().getTime() - match.getMatchInfo().getGameStartDate().getTime()) / 1000 / 60 + " minutes)", false)
                .addField("Attackers", redString, true)
                .addField("Defenders", blueString, true);

        event.getHook().editOriginalEmbeds(embed.build()).queue();
    }

    @Override
    public String getName() {
        return "history";
    }
}