package dev.piste.vayna.interactions.selectmenus.string;

import dev.piste.vayna.interactions.general.SettingsInteraction;
import dev.piste.vayna.interactions.util.interfaces.IStringSelectMenu;
import dev.piste.vayna.mongodb.GuildSetting;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.translations.LanguageManager;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class LanguageSelectMenu implements IStringSelectMenu {

    @Override
    public void perform(StringSelectInteractionEvent event, Language language) {
        event.deferEdit().queue();

        new GuildSetting(event.getGuild().getIdLong())
                .setLocale(event.getSelectedOptions().get(0).getValue())
                .update();

        SettingsInteraction.sendSettingsEmbed(event.getHook(), LanguageManager.getLanguage(event.getGuild()));
    }

    @Override
    public String getName() {
        return "language";
    }
}