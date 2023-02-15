package dev.piste.vayna.commands.button;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.riot.InvalidRegionException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.manager.Button;
import dev.piste.vayna.manager.ButtonManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.ErrorMessages;
import dev.piste.vayna.util.templates.SelectMenus;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

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
        StringSelectMenu stringSelectMenu;
        try {
            stringSelectMenu = SelectMenus.getHistorySelectMenu(event.getGuild(), riotAccount);
        } catch (InvalidRegionException e) {
            event.getHook().editOriginalEmbeds(ErrorMessages.getInvalidRegion(event.getGuild(), event.getUser(), riotAccount)).setActionRow(
                    Buttons.getSupportButton(event.getGuild())
            ).queue();
            return;
        }

        Embed embed = new Embed()
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("button-history-embed-title"))
                .setDescription(language.getTranslation("button-history-embed-description"));

        event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
            stringSelectMenu
        ).queue();

    }

    @Override
    public String getName() {
        return "history;";
    }
}
