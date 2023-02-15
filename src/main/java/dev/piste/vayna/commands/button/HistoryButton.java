package dev.piste.vayna.commands.button;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.riot.InvalidRegionException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.apis.riot.gson.ActiveShard;
import dev.piste.vayna.apis.riot.gson.Match;
import dev.piste.vayna.apis.riot.gson.Matchlist;
import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.apis.riot.gson.match.Player;
import dev.piste.vayna.apis.riot.gson.matchlist.ListedMatch;
import dev.piste.vayna.apis.valorantapi.ValorantAPI;
import dev.piste.vayna.apis.valorantapi.gson.Agent;
import dev.piste.vayna.apis.valorantapi.gson.Map;
import dev.piste.vayna.apis.valorantapi.gson.Queue;
import dev.piste.vayna.commands.selectmenu.HistorySelectMenu;
import dev.piste.vayna.manager.Button;
import dev.piste.vayna.manager.ButtonManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.ErrorMessages;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.util.ArrayList;

/**
 * @author Piste | https://github.com/zPiste
 */
public class HistoryButton implements Button {


    @Override
    public void perform(ButtonInteractionEvent event, String arg) throws StatusCodeException {
        event.deferReply().queue();
        Language language = LanguageManager.getLanguage(event.getGuild());

        RiotAccount oldRiotAccount = ButtonManager.getRiotAccountFromStatsButtonMap(arg);
        if(oldRiotAccount == null) {
            Embed embed = new Embed()
                    .setAuthor(event.getUser().getName(), event.getUser().getAvatarUrl())
                    .setColor(255, 0, 0)
                    .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("button-history-error-old-embed-title"))
                    .setDescription(language.getTranslation("button-history-error-old-embed-description"));
            event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                    Buttons.getSupportButton(event.getGuild())
            ).queue();
            return;
        }

        RiotAccount riotAccount = RiotAPI.getAccountByPuuid(oldRiotAccount.getPuuid());
        ActiveShard activeShard;
        try {
            activeShard = RiotAPI.getActiveShard(riotAccount.getPuuid());
        } catch (InvalidRegionException e) {
            event.getHook().editOriginalEmbeds(ErrorMessages.getInvalidRegion(event.getGuild(), event.getUser(), riotAccount)).setActionRow(
                    Buttons.getSupportButton(event.getGuild())
            ).queue();
            return;
        }
        Matchlist matchlist = RiotAPI.getMatchlist(riotAccount.getPuuid(), activeShard.getActiveShard());

        ArrayList<Map> maps = ValorantAPI.getMaps(language.getLanguageCode());
        ArrayList<Agent> agents = ValorantAPI.getAgents(language.getLanguageCode());
        ArrayList<Queue> queues = ValorantAPI.getQueues(language.getLanguageCode());

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

            selectOptions.add(SelectOption.of(queue.getDropdownText(), match.getMatchInfo().getMatchId())
                    .withDescription(map.getDisplayName() + " (" + agent.getDisplayName() + ")")
                    .withEmoji(Emoji.getQueue(queue.getUuid())
            ));
        }

        Embed embed = new Embed()
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("button-history-embed-title"))
                .setDescription(language.getTranslation("button-history-embed-description"));

        StringSelectMenu stringSelectMenu = StringSelectMenu.create(new HistorySelectMenu().getName())
                .addOptions(selectOptions)
                .setPlaceholder(language.getTranslation("button-history-selectmenu-placeholder"))
                .build();

        event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
            stringSelectMenu
        ).queue();

    }

    @Override
    public String getName() {
        return "history;";
    }
}
