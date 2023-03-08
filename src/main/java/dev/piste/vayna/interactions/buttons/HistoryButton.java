package dev.piste.vayna.interactions.buttons;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.translations.Language;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class HistoryButton implements IButton {


    @Override
    public void perform(ButtonInteractionEvent event, String[] args, Language language) throws HttpErrorException, IOException, InterruptedException {
        /*MessageEmbed.AuthorInfo author = event.getMessage().getEmbeds().get(0).getAuthor();
        event.deferReply().queue();

        RiotGamesAPI riotGamesAPI = new RiotGamesAPI();
        OfficerAPI officerAPI = new OfficerAPI();

        RiotAccount riotAccount = riotGamesAPI.getAccount(args[0]);
        String region = riotGamesAPI.getRegion(riotAccount.getPUUID());
        List<MatchList.Entry> matchList = riotGamesAPI.getMatchList(riotAccount.getPUUID(), region).getHistory();

        List<SelectOption> selectOptions = new ArrayList<>();

        for(int i = 0; i < 25; i++) {
            MatchList.Entry matchListEntry = matchList.get(i);
            MongoMatch mongoMatch = new MongoMatch(matchListEntry.getMatchId());
            if(!mongoMatch.isExisting()) {
                mongoMatch = new MongoMatch(riotGamesAPI.getMatch(matchListEntry.getMatchId(), region));
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
        ).queue();*/

    }

    @Override
    public String getName() {
        return "history";
    }
}
