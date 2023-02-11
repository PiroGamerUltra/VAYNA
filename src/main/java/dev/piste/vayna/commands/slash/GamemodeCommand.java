package dev.piste.vayna.commands.slash;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.valorantapi.ValorantAPI;
import dev.piste.vayna.manager.Command;
import dev.piste.vayna.apis.valorantapi.gson.Gamemode;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

/**
 * @author Piste | https://github.com/zPiste
 */
public class GamemodeCommand implements Command {

    @Override
    public void perform(SlashCommandInteractionEvent event) throws StatusCodeException {
        event.deferReply().setEphemeral(true).queue();
        Language language = LanguageManager.getLanguage(event.getGuild());

        // Searching the gamemode by the provided UUID
        Gamemode gamemode = ValorantAPI.getGamemode(event.getOption("name").getAsString(), language.getLanguageCode());

        // Creating the reply embed
        Embed embed = new Embed()
                .setAuthor(gamemode.getDisplayName(), gamemode.getDisplayIcon())
                .addField(language.getTranslation("command-gamemode-embed-field-1-name"), gamemode.getDuration(), true)
                .removeFooter();

        // Reply
        event.getHook().editOriginalEmbeds(embed.build()).queue();
    }

    @Override
    public CommandData getCommandData() throws StatusCodeException {
        OptionData optionData = new OptionData(OptionType.STRING, "name", "Gamemode name", true);
        for(Gamemode gamemode : ValorantAPI.getGamemodes("en-US")) {
            if(gamemode.getDisplayName().equalsIgnoreCase("Onboarding") || gamemode.getDisplayName().equalsIgnoreCase("PRACTICE")) continue;
            optionData.addChoice(gamemode.getDisplayName(), gamemode.getUuid());
        }
        return Commands.slash(getName(), getDescription()).addOptions(optionData);
    }

    @Override
    public String getName() {
        return "gamemode";
    }

    @Override
    public String getDescription() {
        return LanguageManager.getLanguage().getTranslation("command-gamemode-description");
    }

}
