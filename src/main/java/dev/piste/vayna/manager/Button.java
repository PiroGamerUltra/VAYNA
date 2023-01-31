package dev.piste.vayna.manager;

import dev.piste.vayna.apis.StatusCodeException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

/**
 * @author Piste | https://github.com/zPiste
 */
public interface Button {

    void perform(ButtonInteractionEvent event, String arg) throws StatusCodeException;

    String getName();

}
