package dev.piste.vayna.manager;

import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

/**
 * @author Piste | https://github.com/zPiste
 */
public interface StringSelectMenu {

    void perform(StringSelectInteractionEvent event);

    String getName();

}
