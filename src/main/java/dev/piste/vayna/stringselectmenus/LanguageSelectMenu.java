package dev.piste.vayna.stringselectmenus;

import dev.piste.vayna.manager.StringSelectMenu;
import dev.piste.vayna.mongodb.GuildSetting;
import dev.piste.vayna.util.messages.ReplyMessages;
import dev.piste.vayna.util.selectmenus.SelectMenus;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

/**
 * @author Piste | https://github.com/zPiste
 */
public class LanguageSelectMenu implements StringSelectMenu {


    @Override
    public void perform(StringSelectInteractionEvent event) {
        event.deferReply(true).queue();

        GuildSetting guildSetting = new GuildSetting(event.getGuild().getIdLong());
        guildSetting.update(event.getSelectedOptions().get(0).getValue());

        event.getHook().editOriginalEmbeds(ReplyMessages.getSettings(event.getGuild())).setActionRow(
                SelectMenus.getSettingsSelectMenu(event.getGuild())
        ).queue();

    }

    @Override
    public String getName() {
        return "language";
    }
}
