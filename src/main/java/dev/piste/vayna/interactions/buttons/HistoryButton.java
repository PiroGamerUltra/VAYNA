package dev.piste.vayna.interactions.buttons;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.officer.OfficerAPI;
import dev.piste.vayna.apis.officer.gson.Map;
import dev.piste.vayna.apis.officer.gson.Queue;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.apis.riot.gson.MatchListEntry;
import dev.piste.vayna.apis.riot.gson.RiotAccount;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class HistoryButton implements IButton {


    @Override
    public void perform(ButtonInteractionEvent event, String[] args, Language language) throws HttpErrorException, IOException, InterruptedException {
        MessageEmbed.AuthorInfo author = event.getMessage().getEmbeds().get(0).getAuthor();
        event.deferReply().queue();

        RiotAPI riotAPI = new RiotAPI();
        OfficerAPI officerAPI = new OfficerAPI();

        RiotAccount riotAccount = riotAPI.getAccount(args[0]);
        String region = riotAPI.getRegion(riotAccount.getPuuid());
        List<MatchListEntry> matchList = riotAPI.getMatchList(riotAccount.getPuuid(), region);

        List<SelectOption> selectOptions = new ArrayList<>();

        for(int i = 0; i < 25; i++) {
            MatchListEntry matchListEntry = matchList.get(i);
            MongoMatch mongoMatch = new MongoMatch(matchListEntry.getMatchId());
            if(!mongoMatch.isExisting()) {
                mongoMatch = new MongoMatch(riotAPI.getMatch(matchListEntry.getMatchId(), region));
                mongoMatch.insert();
            }
            Queue queue = officerAPI.getQueue(mongoMatch.getMatchInfo().getQueueId(), language.getLanguageCode());
            Map map = officerAPI.getMap(mongoMatch.getMatchInfo().getMapId(), language.getLanguageCode());
            selectOptions.add(SelectOption.of(queue.getDropdownText(), mongoMatch.getMatchId())
                    .withDescription(map.getDisplayName() + " | " + new SimpleDateFormat("dd/MM/yyyy hh:mm a zzz").format(mongoMatch.getMatchInfo().getStartDate()))
                    .withEmoji(Emojis.getQueue(queue.getUuid())));
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
