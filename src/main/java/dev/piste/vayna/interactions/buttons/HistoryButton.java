package dev.piste.vayna.interactions.buttons;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.ErrorMessages;
import dev.piste.vayna.util.templates.SelectMenus;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class HistoryButton implements Button {


    @Override
    public void perform(ButtonInteractionEvent event, String[] args) throws HttpErrorException, IOException, InterruptedException {
        MessageEmbed.AuthorInfo author = event.getMessage().getEmbeds().get(0).getAuthor();
        event.deferReply().queue();
        Language language = LanguageManager.getLanguage(event.getGuild());

        RiotAccount riotAccount = new RiotAPI().getAccount(args[0]);
        StringSelectMenu stringSelectMenu;
        try {
            stringSelectMenu = SelectMenus.getHistorySelectMenu(event.getGuild(), riotAccount);
        } catch (HttpErrorException e) {
            if(e.getStatusCode() == 404) {
                event.getHook().editOriginalEmbeds(ErrorMessages.getInvalidRegion(event.getGuild(), event.getUser(), riotAccount)).setActionRow(
                        Buttons.getSupportButton(language)
                ).queue();
                return;
            } else {
                throw e;
            }
        }

        Embed embed = new Embed()
                .setAuthor(author.getName(), author.getIconUrl())
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("button-history-embed-title"))
                .setDescription(language.getTranslation("button-history-embed-description"));

        event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
            stringSelectMenu
        ).queue();

    }

    @Override
    public String getName() {
        return "history";
    }
}
