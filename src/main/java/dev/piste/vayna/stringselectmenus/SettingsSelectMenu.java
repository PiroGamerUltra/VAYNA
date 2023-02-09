package dev.piste.vayna.stringselectmenus;

import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Language;
import dev.piste.vayna.util.LanguageManager;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

/**
 * @author Piste | https://github.com/zPiste
 */
public class SettingsSelectMenu implements dev.piste.vayna.manager.StringSelectMenu {


    @Override
    public void perform(StringSelectInteractionEvent event) {
        event.deferReply(true).queue();
        Language language = LanguageManager.getLanguage(event.getGuild());

        if (event.getInteraction().getSelectedOptions().get(0).getValue().equals("language")) {
            Embed embed = new Embed()
                    .setAuthor(event.getGuild().getName(), ConfigManager.getSettingsConfig().getWebsiteUri(), event.getGuild().getIconUrl())
                    .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("stringselect-settings-language-embed-title"))
                    .setDescription(language.getTranslation("stringselect-settings-language-embed-description"));
            StringSelectMenu.Builder stringSelectMenuBuilder = StringSelectMenu.create("language")
                    .setPlaceholder(language.getTranslation("stringselect-settings-language-selectmenu-placeholder"))
                    .setMinValues(1)
                    .setMaxValues(1);
            for(Language foundLanguage : LanguageManager.getLanguages()) {
                stringSelectMenuBuilder.addOption(foundLanguage.getTranslation("language-name"), foundLanguage.getLanguageCode(), Emoji.fromUnicode(foundLanguage.getTranslation("language-emoji")));
            }
            event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                    stringSelectMenuBuilder.build()
            ).queue();
        }
    }

    @Override
    public String getName() {
        return "settings";
    }
}
