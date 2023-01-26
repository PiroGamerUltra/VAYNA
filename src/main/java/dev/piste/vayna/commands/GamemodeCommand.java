package dev.piste.vayna.commands;

import dev.piste.vayna.api.valorantapi.Gamemode;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class GamemodeCommand {

    public static void performCommand(SlashCommandInteractionEvent event) {
        Gamemode gamemode = Gamemode.getGamemodeByName(event.getOption("name").getAsString());

        Embed embed = new Embed();
        embed.setAuthor(event.getUser().getName(), Configs.getSettings().getWebsiteUri(), event.getUser().getAvatarUrl());
        embed.setTitle("» " + gamemode.getDisplayName());
        embed.addField("Average duration", gamemode.getDuration(), true);
        embed.setThumbnail(gamemode.getDisplayIcon());
        event.getHook().editOriginalEmbeds(embed.build()).queue();
    }

}
