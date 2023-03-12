package dev.piste.vayna.interactions.util.interfaces;

import dev.piste.vayna.http.HttpErrorException;
import dev.piste.vayna.translations.Language;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public interface IStringSelectMenu {

    void perform(StringSelectInteractionEvent event, Language language) throws HttpErrorException, IOException, InterruptedException;

    String getName();

}
