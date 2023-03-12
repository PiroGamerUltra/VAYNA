package dev.piste.vayna.interactions.general;

import dev.piste.vayna.interactions.selectmenus.string.SettingsSelectMenu;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class SettingsInteraction {

    public static void sendSettingsEmbed(InteractionHook hook, Language language) {

        Embed embed = new Embed()
                .setAuthor(hook.getInteraction().getGuild().getName(), hook.getInteraction().getGuild().getIconUrl())
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("settings-embed-title"))
                .addField(language.getTranslation("settings-embed-field-1-name"),
                        language.getEmoji() + " " + language.getName(), false);

        StringSelectMenu stringSelectMenu = StringSelectMenu.create(new SettingsSelectMenu().getName())
                .setPlaceholder(language.getTranslation("selectmenu-settings-placeholder"))
                .addOption(language.getTranslation("selectmenu-settings-option-1"), "language", Emoji.fromUnicode("\uD83D\uDDE3️"))
                .build();

        hook.editOriginalEmbeds(embed.build()).setActionRow(
                stringSelectMenu
        ).queue();

    }

}