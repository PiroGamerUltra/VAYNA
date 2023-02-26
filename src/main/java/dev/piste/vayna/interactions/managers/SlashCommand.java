package dev.piste.vayna.interactions.managers;

import dev.piste.vayna.apis.HttpErrorException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/**
 * @author Piste | https://github.com/PisteDev
 */
public interface SlashCommand {

    void perform(SlashCommandInteractionEvent event) throws HttpErrorException;

    CommandData getCommandData() throws HttpErrorException;

    String getName();
    String getDescription();

}
