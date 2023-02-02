package dev.piste.vayna.stringselectmenus;

import dev.piste.vayna.config.Configs;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.TranslationManager;
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

        TranslationManager translation = TranslationManager.getTranslation(event.getGuild());

        if (event.getInteraction().getSelectedOptions().get(0).getValue().equals("language")) {
            Embed embed = new Embed()
                    .setAuthor(event.getGuild().getName(), Configs.getSettings().getWebsiteUri(), event.getGuild().getIconUrl())
                    .setTitle(translation.getTranslation("embed-title-prefix") + translation.getTranslation("stringselect-settings-language-embed-title"))
                    .setDescription(translation.getTranslation("stringselect-settings-language-embed-description"));
            StringSelectMenu stringSelectMenu = StringSelectMenu.create("language")
                    .setPlaceholder(translation.getTranslation("stringselect-settings-language-selectmenu-placeholder"))
                    .setMinValues(1)
                    .setMaxValues(1)
                    .addOption(translation.getTranslation("stringselect-settings-language-selectmenu-option-1"), "en-US", Emoji.fromUnicode("\uD83C\uDDFA\uD83C\uDDF8"))
                    .addOption(translation.getTranslation("stringselect-settings-language-selectmenu-option-2"), "de-DE", Emoji.fromUnicode("\uD83C\uDDE9\uD83C\uDDEA"))
                    .build();
            event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                    stringSelectMenu
            ).queue();
        }
    }

    @Override
    public String getName() {
        return "settings";
    }
}
