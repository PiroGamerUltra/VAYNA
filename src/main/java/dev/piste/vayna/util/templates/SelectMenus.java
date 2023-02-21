package dev.piste.vayna.util.templates;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.riot.InvalidRegionException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.apis.riot.gson.ActiveShard;
import dev.piste.vayna.apis.riot.gson.Match;
import dev.piste.vayna.apis.riot.gson.Matchlist;
import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.apis.riot.gson.match.Player;
import dev.piste.vayna.apis.riot.gson.matchlist.ListedMatch;
import dev.piste.vayna.apis.officer.OfficerAPI;
import dev.piste.vayna.apis.officer.gson.Agent;
import dev.piste.vayna.apis.officer.gson.Map;
import dev.piste.vayna.apis.officer.gson.Queue;
import dev.piste.vayna.commands.selectmenu.HistorySelectMenu;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.util.ArrayList;

/**
 * @author Piste | https://github.com/zPiste
 */
public class SelectMenus {

    public static StringSelectMenu getSettingsSelectMenu(Guild guild) {
        Language language = LanguageManager.getLanguage(guild);

        return StringSelectMenu.create("settings")
                .setPlaceholder(language.getTranslation("command-settings-selectmenu-placeholder"))
                .addOption(language.getTranslation("command-settings-selectmenu-option-1"), "language", Emoji.fromUnicode("\uD83D\uDDE3️"))
                .build();
    }

    public static StringSelectMenu getHistorySelectMenu(Guild guild, RiotAccount riotAccount) throws StatusCodeException, InvalidRegionException {
        Language language = LanguageManager.getLanguage(guild);

        ActiveShard activeShard = RiotAPI.getActiveShard(riotAccount.getPuuid());
        Matchlist matchlist = RiotAPI.getMatchlist(riotAccount.getPuuid(), activeShard.getActiveShard());

        ArrayList<Map> maps = OfficerAPI.getMaps(language.getLanguageCode());
        ArrayList<Agent> agents = OfficerAPI.getAgents(language.getLanguageCode());
        ArrayList<Queue> queues = OfficerAPI.getQueues(language.getLanguageCode());

        ArrayList<SelectOption> selectOptions = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            ListedMatch listedMatch = matchlist.getHistory().get(i);
            Match match = RiotAPI.getMatch(listedMatch.getMatchId(), activeShard.getActiveShard());
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

            selectOptions.add(SelectOption.of(queue.getDropdownText(), match.getMatchInfo().getMatchId() + ";" + activeShard.getActiveShard())
                    .withDescription(map.getDisplayName() + " (" + agent.getDisplayName() + ")")
                    .withEmoji(dev.piste.vayna.util.Emoji.getQueue(queue.getUuid())
                    ));
        }

        return StringSelectMenu.create(new HistorySelectMenu().getName())
                .addOptions(selectOptions)
                .setPlaceholder(language.getTranslation("button-history-selectmenu-placeholder"))
                .build();
    }

}
