package dev.piste.vayna.interactions.buttons;

import dev.piste.vayna.http.HttpErrorException;
import dev.piste.vayna.http.apis.OfficerAPI;
import dev.piste.vayna.http.apis.RiotGamesAPI;
import dev.piste.vayna.http.apis.ValorantAPI;
import dev.piste.vayna.http.models.officer.Agent;
import dev.piste.vayna.http.models.officer.Map;
import dev.piste.vayna.http.models.officer.Queue;
import dev.piste.vayna.http.models.riotgames.Match;
import dev.piste.vayna.http.models.riotgames.MatchList;
import dev.piste.vayna.http.models.riotgames.RiotAccount;
import dev.piste.vayna.interactions.selectmenus.string.HistorySelectMenu;
import dev.piste.vayna.interactions.util.interfaces.IButton;
import dev.piste.vayna.mongodb.MongoMatch;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.DiscordEmoji;
import dev.piste.vayna.util.RiotRegion;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class HistoryButton implements IButton {


    @Override
    public void perform(ButtonInteractionEvent event, String[] args, Language language) throws HttpErrorException, IOException, InterruptedException {
        MessageEmbed.AuthorInfo author = event.getMessage().getEmbeds().get(0).getAuthor();
        event.deferReply().queue();

        RiotGamesAPI riotGamesAPI = new RiotGamesAPI();
        OfficerAPI officerAPI = new OfficerAPI();

        RiotAccount riotAccount = riotGamesAPI.getAccount(args[0]);
        RiotRegion region = RiotRegion.getRiotRegionById(riotGamesAPI.getRegion(riotAccount.getPUUID()));
        ValorantAPI valorantAPI = new ValorantAPI(region);
        List<MatchList.Entry> matchList = valorantAPI.getMatchList(riotAccount.getPUUID()).getHistory();

        List<SelectOption> selectOptions = new ArrayList<>();

        List<Agent> agents = officerAPI.getAgents(language);
        List<Map> maps = officerAPI.getMaps(language);
        List<Queue> queues = officerAPI.getQueues(language);

        for(int i = 0; i < 25; i++) {
            if(matchList.size() <= i) break;
            MatchList.Entry matchListEntry = matchList.get(i);
            MongoMatch mongoMatch = new MongoMatch(matchListEntry.getMatchId());
            Match match;
            if(mongoMatch.isExisting()) {
                match = mongoMatch.getMatch();
            } else {
                match = new MongoMatch(valorantAPI.getMatch(matchListEntry.getMatchId())).insert().getMatch();
            }
            Queue queue = queues.stream()
                    .filter(foundQueue -> foundQueue.getName().equalsIgnoreCase(match.getMatchInfo().getQueueName()))
                    .findFirst()
                    .orElse(null);
            if(queue == null) throw new NullPointerException("Queue is null! (Match ID: " + match.getMatchInfo().getMatchId() + ")");
            Map map = maps.stream()
                    .filter(foundMap -> foundMap.getPath().equalsIgnoreCase(match.getMatchInfo().getMapPath()))
                    .findFirst()
                    .orElse(null);
            if(map == null) throw new NullPointerException("Map is null! (Match ID: " + match.getMatchInfo().getMatchId() + ")");
            String agentName = "N/A";
            for(Match.Player player : match.getPlayers()) {
                if(riotAccount.getPUUID().equalsIgnoreCase(player.getPUUID())) {
                    for(Agent agent : agents) {
                        if(agent.getId().equalsIgnoreCase(player.getAgentId())) {
                            agentName = agent.getDisplayName();
                            break;
                        }
                    }
                    break;
                }
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a zzz");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Europe/Berlin")));
            selectOptions.add(SelectOption.of(queue.getDropdownText() + " (" + map.getDisplayName() + ")", match.getMatchInfo().getMatchId() + ";" + region.getId())
                    .withDescription(agentName + " - " + simpleDateFormat.format(match.getMatchInfo().getGameStartDate()))
                    .withEmoji(DiscordEmoji.Queue.getQueueById(queue.getId()).getAsDiscordEmoji()));
        }

        Embed embed = new Embed()
                .setAuthor(author.getName(), author.getIconUrl())
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("button-history-embed-title"))
                .setDescription(language.getTranslation("button-history-embed-desc"));

        StringSelectMenu stringSelectMenu = StringSelectMenu.create(new HistorySelectMenu().getName())
                .addOptions(selectOptions)
                .setPlaceholder(language.getTranslation("selectmenu-history-placeholder"))
                .build();

        event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                stringSelectMenu
        ).queue();

    }

    @Override
    public String getName() {
        return "history";
    }
}
