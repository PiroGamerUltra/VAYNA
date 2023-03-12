package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.http.HttpErrorException;
import dev.piste.vayna.http.apis.HenrikAPI;
import dev.piste.vayna.http.models.henrik.News;
import dev.piste.vayna.http.models.henrik.NewsCategory;
import dev.piste.vayna.interactions.general.PatchInteraction;
import dev.piste.vayna.interactions.util.exceptions.*;
import dev.piste.vayna.interactions.util.interfaces.ISlashCommand;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.translations.LanguageManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.io.IOException;
import java.util.List;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class PatchSlashCommand implements ISlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event, Language language) throws HttpErrorException, IOException, InterruptedException, InvalidRiotIdException, GuildConnectionsMissingException, InsufficientPermissionException, RSOConnectionPrivateException, InvalidRegionException, RSOConnectionMissingException {
        event.deferReply(true).queue();

        List<News> newsList = new HenrikAPI().getNews(language);

        News latestPatchNotes = newsList.stream()
                .filter(news -> news.getCategory() == NewsCategory.PATCH_NOTES)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("No Patch Notes found"));

        PatchInteraction.sendPatchNotesEmbed(newsList, latestPatchNotes, event.getHook(), language);
    }

    @Override
    public String getName() {
        return "patch";
    }

    @Override
    public String getDescription(Language language) {
        return language.getTranslation("command-patchnotes-desc");
    }

    @Override
    public CommandData getCommandData() throws HttpErrorException, IOException, InterruptedException {
        return Commands.slash(getName(), getDescription(LanguageManager.getDefaultLanguage()));
    }

}