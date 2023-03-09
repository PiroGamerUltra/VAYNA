package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.OfficerAPI;
import dev.piste.vayna.apis.entities.officer.GameMode;
import dev.piste.vayna.apis.entities.officer.Queue;
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
public class GameModeSlashCommand implements ISlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event, Language language) throws HttpErrorException, IOException, InterruptedException {
        event.deferReply(true).queue();

        Queue queue = new OfficerAPI().getQueue(event.getOption("name").getAsString(), language.getLocale());
        GameMode gameMode = queue.getParentGameMode(language.getLocale());

        Embed embed = new Embed()
                .setAuthor(queue.getDropdownText() + (queue.isBeta() ? " " + language.getTranslation("command-gamemode-embed-author") : ""), gameMode.getDisplayIcon())
                .setDescription(">>> " + queue.getDescription())
                .addField(language.getTranslation("command-gamemode-embed-field-1-name"), gameMode.getDuration(), true)
                .removeFooter();

        event.getHook().editOriginalEmbeds(embed.build()).queue();
    }

    @Override
    public String getName() {
        return "gamemode";
    }

    @Override
    public String getDescription(Language language) {
        return language.getTranslation("command-gamemode-desc");
    }

    @Override
    public CommandData getCommandData() throws HttpErrorException, IOException, InterruptedException {
        OptionData optionData = new OptionData(OptionType.STRING, "name", "The name of the gamemode", true);
        for(Queue queue : new OfficerAPI().getQueues(LanguageManager.getDefaultLanguage().getLocale())) {
            if(queue.getName().equals("custom") || queue.getName().equals("newmap")) continue;
            optionData.addChoice(queue.getDropdownText(), queue.getId());
        }
        return Commands.slash(getName(), getDescription(LanguageManager.getDefaultLanguage())).addOptions(optionData);
    }

}