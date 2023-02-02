package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.valorantapi.ValorantAPI;
import dev.piste.vayna.manager.Command;
import dev.piste.vayna.apis.valorantapi.gson.Map;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.TranslationManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class MapCommand implements Command {

    @Override
    public void perform(SlashCommandInteractionEvent event) throws StatusCodeException {
        event.deferReply().queue();

        TranslationManager translation = TranslationManager.getTranslation(event.getGuild());

        String uuid = ValorantAPI.getMapByName(event.getOption("name").getAsString(), "en-US").getUuid();
        Map map = ValorantAPI.getMap(uuid, translation.getLanguageCode());

        Embed embed = new Embed();
        embed.setAuthor(event.getUser().getName(), Configs.getSettings().getWebsiteUri(), event.getUser().getAvatarUrl());
        embed.setTitle(translation.getTranslation("embed-title-prefix") + map.getDisplayName());
        embed.addField(translation.getTranslation("command-map-embed-field-1-name"), map.getCoordinates(), true);
        embed.setImage(map.getSplash());
        embed.setThumbnail(map.getDisplayIcon());
        event.getHook().editOriginalEmbeds(embed.build()).queue();
    }

    @Override
    public void register() throws StatusCodeException {
        OptionData optionData = new OptionData(OptionType.STRING, "name", "Name of the map", true);
        for(Map map : ValorantAPI.getMaps("en-US")) {
            if(map.getDisplayName().equalsIgnoreCase("The Range")) continue;
            optionData.addChoice(map.getDisplayName(), map.getDisplayName());
        }
        Bot.getJDA().upsertCommand(getName(), getDescription()).addOptions(optionData).queue();
    }

    @Override
    public String getName() {
        return "map";
    }

    @Override
    public String getDescription() {
        return "Get information about a specific VALORANT map";
    }

}
