package dev.piste.vayna.commands.selectmenu;

import dev.piste.vayna.manager.StringSelectMenu;
import dev.piste.vayna.mongodb.GuildSetting;
import dev.piste.vayna.util.templates.ReplyMessages;
import dev.piste.vayna.util.templates.SelectMenus;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

/**
 * @author Piste | https://github.com/zPiste
 */
public class LanguageSelectMenu implements StringSelectMenu {

    @Override
    public void perform(StringSelectInteractionEvent event) {
        GuildSetting guildSetting = new GuildSetting(event.getGuild().getIdLong());
        guildSetting.setLanguage(event.getSelectedOptions().get(0).getValue());
        guildSetting.update();

        // Reply
        event.editMessageEmbeds(ReplyMessages.getSettings(event.getGuild())).setActionRow(
                SelectMenus.getSettingsSelectMenu(event.getGuild())
        ).queue();
    }

    @Override
    public String getName() {
        return "language";
    }
}
