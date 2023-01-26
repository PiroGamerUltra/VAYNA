package dev.piste.vayna.commands;

import dev.piste.vayna.api.valorantapi.Map;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class MapCommand {

    public static void performCommand(SlashCommandInteractionEvent event) {
        Map map = Map.getMapByName(event.getOption("name").getAsString());

        Embed embed = new Embed();
        embed.setAuthor(event.getUser().getName(), Configs.getSettings().getWebsiteUri(), event.getUser().getAvatarUrl());
        embed.setTitle("» " + map.getDisplayName());
        embed.addField("Coordinates", map.getCoordinates(), true);
        embed.setImage(map.getSplash());
        embed.setThumbnail(map.getDisplayIcon());
        event.getHook().editOriginalEmbeds(embed.build()).queue();
    }

}
