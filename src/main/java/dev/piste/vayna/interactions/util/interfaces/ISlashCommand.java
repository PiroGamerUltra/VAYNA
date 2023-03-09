package dev.piste.vayna.interactions.util.interfaces;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.interactions.util.exceptions.*;
import dev.piste.vayna.translations.Language;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public interface ISlashCommand {

    void perform(SlashCommandInteractionEvent event, Language language) throws HttpErrorException, IOException, InterruptedException, InvalidRiotIdException, GuildConnectionsMissingException, InsufficientPermissionException, RSOConnectionPrivateException, InvalidRegionException, RSOConnectionMissingException;

    String getName();

    String getDescription(Language language);

    CommandData getCommandData() throws HttpErrorException, IOException, InterruptedException;

}