package dev.piste.vayna.listener;

import dev.piste.vayna.commands.*;
import dev.piste.vayna.manager.CommandManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommandListener extends ListenerAdapter {

    private final CommandManager commandManager = new CommandManager();

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        commandManager.perform(event);
    }

}
