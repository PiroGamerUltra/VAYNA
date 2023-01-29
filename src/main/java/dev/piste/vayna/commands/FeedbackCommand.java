package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.manager.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

/**
 * @author Piste | https://github.com/zPiste
 */
public class FeedbackCommand implements Command {

    @Override
    public void perform(SlashCommandInteractionEvent event) {
        TextInput feedbackInput = TextInput.create("feedback", "Feedback", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Enter your feedback here")
                .setMaxLength(1000)
                .build();
        Modal modal = Modal.create("test", "test1")
                .addActionRows(ActionRow.of(feedbackInput))
                .build();

        event.replyModal(modal).queue();
    }

    @Override
    public void register() {
        Bot.getJDA().upsertCommand(getName(), getDescription()).queue();
    }

    @Override
    public String getName() {
        return "feedback";
    }

    @Override
    public String getDescription() {
        return "Share feedback to the developer of VAYNA";
    }
}
