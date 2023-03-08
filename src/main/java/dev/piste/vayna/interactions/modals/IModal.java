package dev.piste.vayna.interactions.modals;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.translations.Language;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

/**
 * @author Piste | https://github.com/PisteDev
 */
public interface IModal {

    void perform(ModalInteractionEvent event, Language language) throws HttpErrorException;

    String getName();

}
