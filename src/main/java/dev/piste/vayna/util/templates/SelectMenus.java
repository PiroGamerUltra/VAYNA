package dev.piste.vayna.util.templates;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.apis.riot.gson.Match;
import dev.piste.vayna.apis.riot.gson.MatchListEntry;
import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.apis.riot.gson.match.Player;
import dev.piste.vayna.apis.officer.OfficerAPI;
import dev.piste.vayna.apis.officer.gson.Agent;
import dev.piste.vayna.apis.officer.gson.Map;
import dev.piste.vayna.apis.officer.gson.Queue;
import dev.piste.vayna.interactions.selectmenus.string.HistorySelectMenu;
import dev.piste.vayna.util.Emojis;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.util.ArrayList;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class SelectMenus {

    public static StringSelectMenu getSettingsSelectMenu(Guild guild) {
        Language language = LanguageManager.getLanguage(guild);

        return StringSelectMenu.create("settings")
                .setPlaceholder(language.getTranslation("command-settings-selectmenu-placeholder"))
                .addOption(language.getTranslation("command-settings-selectmenu-option-1"), "language", Emoji.fromUnicode("\uD83D\uDDE3️"))
                .build();
    }

    public static StringSelectMenu getHistorySelectMenu(Guild guild, RiotAccount riotAccount) throws HttpErrorException {
        Language language = LanguageManager.getLanguage(guild);

        OfficerAPI officerAPI = new OfficerAPI();
        RiotAPI riotAPI = new RiotAPI();

        String region = riotAPI.getRegion(riotAccount.getPuuid());
        ArrayList<MatchListEntry> matchList = riotAPI.getMatchList(riotAccount.getPuuid(), region);

        ArrayList<Map> maps = officerAPI.getMaps(language.getLanguageCode());
        ArrayList<Agent> agents = officerAPI.getAgents(language.getLanguageCode());
        ArrayList<Queue> queues = officerAPI.getQueues(language.getLanguageCode());

        ArrayList<SelectOption> selectOptions = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            MatchListEntry matchListEntry = matchList.get(i);
            Match match = riotAPI.getMatch(matchListEntry.getMatchId(), region);
            Player player = null;
            for(Player foundPlayer : match.getPlayers()) {
                if(foundPlayer.getPuuid().equals(riotAccount.getPuuid())) player = foundPlayer;
            }
            Map map = null;
            for(Map foundMap : maps) {
                if(foundMap.getMapUrl().equals(match.getMatchInfo().getMapId())) map = foundMap;
            }
            Agent agent = null;
            for(Agent foundAgent : agents) {
                if(foundAgent.getUuid().equals(player.getCharacterId())) agent = foundAgent;
            }
            Queue queue = null;
            if(match.getMatchInfo().getQueueId().equals("")) {
                for(Queue foundQueue : queues) {
                    if(foundQueue.getQueueId().equals("custom")) queue = foundQueue;
                }
            } else {
                for(Queue foundQueue : queues) {
                    if(foundQueue.getQueueId().equals(match.getMatchInfo().getQueueId())) queue = foundQueue;
                }
            }

            selectOptions.add(SelectOption.of(queue.getDropdownText(), match.getMatchInfo().getMatchId() + ";" + region)
                    .withDescription(map.getDisplayName() + " (" + agent.getDisplayName() + ")")
                    .withEmoji(Emojis.getQueue(queue.getUuid())
                    ));
        }

        return StringSelectMenu.create(new HistorySelectMenu().getName())
                .addOptions(selectOptions)
                .setPlaceholder(language.getTranslation("button-history-selectmenu-placeholder"))
                .build();
    }

}
