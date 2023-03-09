package dev.piste.vayna.interactions.util.interfaces;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.interactions.util.exceptions.InvalidRegionException;
import dev.piste.vayna.interactions.util.exceptions.RSOConnectionMissingException;
import dev.piste.vayna.interactions.util.exceptions.RSOConnectionPrivateException;
import dev.piste.vayna.translations.Language;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public interface IUserContextCommand {

    void perform(UserContextInteractionEvent event, Language language) throws HttpErrorException, IOException, InterruptedException, RSOConnectionPrivateException, InvalidRegionException, RSOConnectionMissingException;

    CommandData getCommandData();

    String getDisplayName();

}