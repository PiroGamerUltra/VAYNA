package dev.piste.vayna.interactions.selectmenus.string;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.interactions.util.interfaces.IStringSelectMenu;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.translations.LanguageManager;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class SettingsSelectMenu implements IStringSelectMenu {

    @Override
    public void perform(StringSelectInteractionEvent event, Language language) throws HttpErrorException {
        event.deferEdit().queue();

        switch (event.getSelectedOptions().get(0).getValue()) {
            case "language" -> {
                Embed embed = new Embed()
                        .setAuthor(event.getGuild().getName(), event.getGuild().getIconUrl())
                        .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("selectmenu-settings-language-embed-title"))
                        .setDescription(language.getTranslation("selectmenu-settings-language-embed-desc"));
                StringSelectMenu.Builder stringSelectMenuBuilder = StringSelectMenu.create(new LanguageSelectMenu().getName())
                        .setPlaceholder(language.getTranslation("selectmenu-language-placeholder"));
                for(Language foundLanguage : LanguageManager.getLanguages()) {
                    stringSelectMenuBuilder.addOption(foundLanguage.getName(), foundLanguage.getLocale(), Emoji.fromUnicode(foundLanguage.getEmoji()));
                }

                event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                        stringSelectMenuBuilder.build()
                ).queue();
            }
        }
    }

    @Override
    public String getName() {
        return "settings";
    }
}