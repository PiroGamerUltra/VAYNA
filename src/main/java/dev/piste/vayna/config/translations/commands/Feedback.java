package dev.piste.vayna.config.translations.commands;

import dev.piste.vayna.modals.FeedbackModal;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Feedback {

    private String modalTitle;
    private String textInputLabel;
    private String textInputPlaceholder;

    public Modal getModal() {
        TextInput feedbackInput = TextInput.create("feedback", textInputLabel, TextInputStyle.PARAGRAPH)
                .setPlaceholder(textInputPlaceholder)
                .setMaxLength(4000)
                .build();
        return Modal.create(new FeedbackModal().getName(), modalTitle)
                .addActionRows(ActionRow.of(feedbackInput))
                .build();
    }

}
