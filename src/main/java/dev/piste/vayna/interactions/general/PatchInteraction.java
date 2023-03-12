package dev.piste.vayna.interactions.general;

import dev.piste.vayna.http.models.henrik.News;
import dev.piste.vayna.http.models.henrik.NewsCategory;
import dev.piste.vayna.interactions.selectmenus.string.PatchSelectMenu;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.UnicodeEmoji;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class PatchInteraction {

    public static void sendPatchNotesEmbed(List<News> newsList, News patchNotes, InteractionHook hook, Language language) {

        Embed embed = new Embed()
                .setTitle(language.getEmbedTitlePrefix() + patchNotes.getTitle(), patchNotes.getURI())
                .setImage(patchNotes.getBanner())
                .addField(language.getTranslation("command-patchnotes-embed-field-1-name"), UnicodeEmoji.CALENDAR.getUnicode() + " <t:" + patchNotes.getDate().getTime() / 1000 + ":D>", true);

        List<SelectOption> selectOptions = new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a zzz");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Europe/Berlin")));

        for(News news : newsList) {
            if(selectOptions.size() >= 25) break;
            if(news.getCategory() == NewsCategory.PATCH_NOTES) {
                Pattern pattern = Pattern.compile("\\d+\\.\\d+");
                Matcher matcher = pattern.matcher(news.getTitle());
                if (matcher.find()) {
                    String match = matcher.group();
                    float patch = Float.parseFloat(match);
                    selectOptions.add(SelectOption.of(String.valueOf(patch), news.getTitle()).withDescription(simpleDateFormat.format(news.getDate())));
                }
            }
        }

        StringSelectMenu stringSelectMenu = StringSelectMenu.create(new PatchSelectMenu().getName())
                .setPlaceholder(language.getTranslation("selectmenu-patchnotes-placeholder"))
                .addOptions(selectOptions)
                .build();

        hook.editOriginalEmbeds(embed.build()).setActionRow(
                stringSelectMenu
        ).queue();

    }

}