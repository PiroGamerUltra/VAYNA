package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.interactions.util.interfaces.ISlashCommand;
import dev.piste.vayna.interactions.modals.FeedbackModal;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.translations.LanguageManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class FeedbackSlashCommand implements ISlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event, Language language) {

        TextInput feedbackInput = TextInput.create("feedback", language.getTranslation("modal-feedback-input-name"), TextInputStyle.PARAGRAPH)
                .setPlaceholder(language.getTranslation("modal-feedback-input-placeholder"))
                .setMaxLength(4000)
                .build();
        Modal modal = Modal.create(new FeedbackModal().getName(), language.getTranslation("modal-feedback-title"))
                .addActionRow(feedbackInput)
                .build();

        event.replyModal(modal).queue();
    }

    @Override
    public String getName() {
        return "feedback";
    }

    @Override
    public String getDescription(Language language) {
        return language.getTranslation("command-feedback-desc");
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(getName(), getDescription(LanguageManager.getDefaultLanguage()));
    }
}