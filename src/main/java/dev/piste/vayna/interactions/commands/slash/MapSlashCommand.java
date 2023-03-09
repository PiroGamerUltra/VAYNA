package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.OfficerAPI;
import dev.piste.vayna.apis.entities.officer.Map;
import dev.piste.vayna.interactions.util.interfaces.ISlashCommand;
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
        event.deferReply(true).queue();

        Map map = new OfficerAPI().getMap(event.getOption("name").getAsString(), language.getLocale());

        Embed embed = new Embed()
                .setAuthor(map.getDisplayName(), map.getSplash())
                .addField(language.getTranslation("command-map-embed-field-1-name"), map.getCoordinates(), true)
                .setImage(map.getSplash())
                .setThumbnail(map.getDisplayIcon());

        event.getHook().editOriginalEmbeds(embed.build()).queue();
    }

    @Override
    public String getName() {
        return "map";
    }

    @Override
    public String getDescription(Language language) {
        return language.getTranslation("command-map-desc");
    }

    @Override
    public CommandData getCommandData() throws HttpErrorException, IOException, InterruptedException {
        OptionData optionData = new OptionData(OptionType.STRING, "name", "The name of the map", true);
        for(Map map : new OfficerAPI().getMaps(LanguageManager.getDefaultLanguage().getLocale())) {
            if(map.getDisplayName().equalsIgnoreCase("The Range")) continue;
            optionData.addChoice(map.getDisplayName(), map.getId());
        }
        return Commands.slash(getName(), getDescription(LanguageManager.getDefaultLanguage())).addOptions(optionData);
    }

}