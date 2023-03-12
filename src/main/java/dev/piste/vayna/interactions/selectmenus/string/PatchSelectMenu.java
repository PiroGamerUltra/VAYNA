package dev.piste.vayna.interactions.selectmenus.string;

import dev.piste.vayna.http.HttpErrorException;
import dev.piste.vayna.http.apis.HenrikAPI;
import dev.piste.vayna.http.models.henrik.News;
import dev.piste.vayna.interactions.general.PatchInteraction;
import dev.piste.vayna.interactions.util.interfaces.IStringSelectMenu;
import dev.piste.vayna.translations.Language;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

import java.io.IOException;
import java.util.List;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class PatchSelectMenu implements IStringSelectMenu {

    @Override
    public void perform(StringSelectInteractionEvent event, Language language) throws HttpErrorException, IOException, InterruptedException {
        event.deferEdit().queue();

        List<News> newsList = new HenrikAPI().getNews(language);

        News selectedNews = null;
        for(News news : newsList) {
            if(news.getTitle().equalsIgnoreCase(event.getValues().get(0))) {
                selectedNews = news;
            }
        }

        PatchInteraction.sendPatchNotesEmbed(newsList, selectedNews, event.getHook(), language);
    }

    @Override
    public String getName() {
        return "patch";
    }
}