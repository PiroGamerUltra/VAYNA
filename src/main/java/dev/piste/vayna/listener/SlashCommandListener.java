package dev.piste.vayna.listener;

import dev.piste.vayna.commands.*;
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
                ConnectionCommand.performCommand(event);
            }
            case "stats" -> {
                event.deferReply().queue();
                StatsCommand.performCommand(event);
            }
            case "map" -> {
                event.deferReply().queue();
                MapCommand.performCommand(event);
            }
            case "agent" -> {
                event.deferReply().queue();
                AgentCommand.performCommand(event);
            }
            case "gamemode" -> {
                event.deferReply().queue();
                GamemodeCommand.performCommand(event);
            }
        }

    }

}
