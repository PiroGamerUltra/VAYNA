package dev.piste.vayna.interactions.buttons;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.OfficerAPI;
import dev.piste.vayna.apis.RiotGamesAPI;
import dev.piste.vayna.apis.entities.officer.Agent;
import dev.piste.vayna.apis.entities.officer.Map;
import dev.piste.vayna.apis.entities.officer.Queue;
import dev.piste.vayna.apis.entities.riotgames.Match;
import dev.piste.vayna.apis.entities.riotgames.MatchList;
import dev.piste.vayna.apis.entities.riotgames.RiotAccount;
import dev.piste.vayna.interactions.util.interfaces.IButton;
import dev.piste.vayna.mongodb.MongoMatch;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emojis;
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
        String region = riotGamesAPI.getRegion(riotAccount.getPUUID());
        List<MatchList.Entry> matchList = riotGamesAPI.getMatchList(riotAccount.getPUUID(), region).getHistory();

        List<SelectOption> selectOptions = new ArrayList<>();

        List<Agent> agents = officerAPI.getAgents(language.getLocale());
        List<Map> maps = officerAPI.getMaps(language.getLocale());
        List<Queue> queues = officerAPI.getQueues(language.getLocale());

        for(int i = 0; i < 25; i++) {
            if(matchList.size() <= i) break;
            MatchList.Entry matchListEntry = matchList.get(i);
            MongoMatch mongoMatch = new MongoMatch(matchListEntry.getMatchId());
            Match match;
            if(mongoMatch.isExisting()) {
                match = mongoMatch.getMatch();
            } else {
                match = new MongoMatch(riotGamesAPI.getMatch(matchListEntry.getMatchId(), region)).insert().getMatch();
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
            selectOptions.add(SelectOption.of(queue.getDropdownText() + " (" + map.getDisplayName() + ")", match.getMatchInfo().getMatchId() + ";" + region)
                    .withDescription(agentName + " - " + simpleDateFormat.format(match.getMatchInfo().getGameStartDate()))
                    .withEmoji(Emojis.getQueue(queue.getName())));
        }

        Embed embed = new Embed()
                .setAuthor(author.getName(), author.getIconUrl())
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("button-history-embed-title"))
                .setDescription(language.getTranslation("button-history-embed-description"));

        StringSelectMenu stringSelectMenu = StringSelectMenu.create("history")
                .addOptions(selectOptions)
                .setPlaceholder(language.getTranslation("button-history-selectmenu-placeholder"))
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
