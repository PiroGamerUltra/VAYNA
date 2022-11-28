package dev.piste.vayna.listener;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommandInteraction extends ListenerAdapter {

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        System.out.println("slash");

    }

}
