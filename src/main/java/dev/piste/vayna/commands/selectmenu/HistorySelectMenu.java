package dev.piste.vayna.commands.selectmenu;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.apis.riot.gson.Match;
import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.apis.riot.gson.match.Player;
import dev.piste.vayna.apis.valorantapi.ValorantAPI;
import dev.piste.vayna.apis.valorantapi.gson.Map;
import dev.piste.vayna.manager.StringSelectMenu;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

/**
 * @author Piste | https://github.com/zPiste
 */
public class HistorySelectMenu implements StringSelectMenu {

    @Override
    public void perform(StringSelectInteractionEvent event) throws StatusCodeException {
        Language language = LanguageManager.getLanguage(event.getGuild());
        event.editMessageEmbeds(new Embed().setTitle("Loading").build()).queue();

        String[] args = event.getSelectedOptions().get(0).getValue().split(";");

        Match match = RiotAPI.getMatch(args[0], args[1]);
        Map map = null;
        for(Map foundMap : ValorantAPI.getMaps(language.getLanguageCode())) {
            if(foundMap.getMapUrl().equals(match.getMatchInfo().getMapId())) map = foundMap;
        }

        Embed embed = new Embed();

        LinkedAccount linkedAccount = new LinkedAccount(event.getUser().getIdLong());
        if(linkedAccount.isExisting()) {
            RiotAccount riotAccount = RiotAPI.getAccountByPuuid(linkedAccount.getRiotPuuid());
            boolean hasPlayedMatch = false;
            for(Player player : match.getPlayers()) {
                if(player.getPuuid().equals(riotAccount.getPuuid())) {
                    embed.setAuthor(riotAccount.getRiotId(), ValorantAPI.getPlayercard(player.getPlayerCard(), language.getLanguageCode()).getSmallArt());
                    hasPlayedMatch = true;
                }
            }
            if(!hasPlayedMatch) {
                embed.setAuthor(event.getUser().getAsTag(), event.getUser().getAvatarUrl());
            }
        } else {
            embed.setAuthor(event.getUser().getAsTag(), event.getUser().getAvatarUrl());
        }

        event.getHook().editOriginalEmbeds(embed.build()).queue();

    }

    @Override
    public String getName() {
        return "history";
    }
}
