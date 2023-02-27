package dev.piste.vayna.interactions.selectmenus.string;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.apis.riot.gson.Match;
import dev.piste.vayna.apis.riot.gson.match.Player;
import dev.piste.vayna.apis.officer.OfficerAPI;
import dev.piste.vayna.apis.officer.gson.Map;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class HistorySelectMenu implements StringSelectMenu {

    @Override
    public void perform(StringSelectInteractionEvent event) throws HttpErrorException, IOException, InterruptedException {
        Language language = LanguageManager.getLanguage(event.getGuild());
        MessageEmbed.AuthorInfo author = event.getMessage().getEmbeds().get(0).getAuthor();
        event.editMessageEmbeds(new Embed().setTitle("Fetching...").build()).queue();

        String[] args = event.getSelectedOptions().get(0).getValue().split(";");

        Match match = new RiotAPI().getMatch(args[0], args[1]);
        Map map = null;
        for(Map foundMap : new OfficerAPI().getMaps(language.getLanguageCode())) {
            if(foundMap.getMapUrl().equals(match.getMatchInfo().getMapId())) map = foundMap;
        }

        Embed embed = new Embed();
        embed.setAuthor(author.getName(), author.getIconUrl());
        embed.setImage(map.getListViewIcon());
        embed.addField("Map", map.getDisplayName(), true);
        embed.addField("Time", "<t:" + Math.round((float) match.getMatchInfo().getGameStartMillis()/1000) + ">", true);

        ArrayList<SelectOption> selectOptions = new ArrayList<>();
        for(Player player : match.getPlayers()) {
            if(player.getTeamId().equals("Blue")) {
                selectOptions.add(SelectOption.of(player.getGameName() + "#" + player.getTagLine(), player.getPuuid()).withEmoji(Emoji.fromUnicode("\uD83D\uDD35")));
            } else if(player.getTeamId().equals("Red")) {
                selectOptions.add(SelectOption.of(player.getGameName() + "#" + player.getTagLine(), player.getPuuid()).withEmoji(Emoji.fromUnicode("\uD83D\uDD34")));
            } else {
                selectOptions.add(SelectOption.of(player.getGameName() + "#" + player.getTagLine(), player.getPuuid()));
            }
        }

        net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu stringSelectMenu = net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu.create("matchPlayers")
                .addOptions(selectOptions)
                .setPlaceholder("Select a player")
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
