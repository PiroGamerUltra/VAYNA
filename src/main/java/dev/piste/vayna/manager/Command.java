package dev.piste.vayna.manager;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.henrik.HenrikApiException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public interface Command {

    void perform(SlashCommandInteractionEvent event) throws StatusCodeException;

    void register() throws StatusCodeException;

    String getName();
    String getDescription();

}
