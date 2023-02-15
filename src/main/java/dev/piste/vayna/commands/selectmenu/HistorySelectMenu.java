package dev.piste.vayna.commands.selectmenu;

import dev.piste.vayna.manager.StringSelectMenu;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

/**
 * @author Piste | https://github.com/zPiste
 */
public class HistorySelectMenu implements StringSelectMenu {


    @Override
    public void perform(StringSelectInteractionEvent event) {

    }

    @Override
    public String getName() {
        return "history";
    }
}
