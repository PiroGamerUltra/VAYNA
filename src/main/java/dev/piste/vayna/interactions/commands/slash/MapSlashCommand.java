package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.officer.OfficerAPI;
import dev.piste.vayna.apis.officer.gson.Map;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.translations.LanguageManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class MapSlashCommand implements ISlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event, Language language) throws HttpErrorException, IOException, InterruptedException {
        event.deferReply().setEphemeral(true).queue();

        // Searching the map by the provided UUID
        Map map = new OfficerAPI().getMap(event.getOption("name").getAsString(), language.getLanguageCode());

        // Creating the reply embed
        Embed embed = new Embed()
                .setAuthor(map.getDisplayName(), map.getSplash())
                .addField(language.getTranslation("command-map-embed-field-1-name"), map.getCoordinates(), true)
                .setImage(map.getSplash())
                .setThumbnail(map.getDisplayIcon());

        // Reply
        event.getHook().editOriginalEmbeds(embed.build()).queue();
    }

    @Override
    public CommandData getCommandData() throws HttpErrorException, IOException, InterruptedException {
        OptionData optionData = new OptionData(OptionType.STRING, "name", "Map name", true);
        for(Map map : new OfficerAPI().getMaps("en-US")) {
            if(map.getDisplayName().equalsIgnoreCase("The Range")) continue;
            optionData.addChoice(map.getDisplayName(), map.getUuid());
        }
        return Commands.slash(getName(), getDescription()).addOptions(optionData);
    }

    @Override
    public String getName() {
        return "map";
    }

    @Override
    public String getDescription() {
        return LanguageManager.getLanguage().getTranslation("command-map-description");
    }

}
