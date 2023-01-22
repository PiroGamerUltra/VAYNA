package dev.piste.vayna.listener;

import dev.piste.vayna.commands.HelpCommand;
import dev.piste.vayna.commands.ConnectionCommand;
import dev.piste.vayna.commands.StatsCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        switch (event.getName().toLowerCase()) {
            case "help" -> {
                event.deferReply().setEphemeral(true).queue();
                HelpCommand.performCommand(event);
            }
            case "connection" -> {
                event.deferReply().setEphemeral(true).queue();
                try {
                    ConnectionCommand.performCommand(event);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "stats" -> {
                event.deferReply().queue();
                StatsCommand.performCommand(event);
            }
        }

    }

}
