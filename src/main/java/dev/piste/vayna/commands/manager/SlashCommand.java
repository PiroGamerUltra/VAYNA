package dev.piste.vayna.commands.manager;

import dev.piste.vayna.apis.StatusCodeException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public interface SlashCommand {

    void perform(SlashCommandInteractionEvent event) throws StatusCodeException;

    CommandData getCommandData() throws StatusCodeException;

    String getName();
    String getDescription();

}
