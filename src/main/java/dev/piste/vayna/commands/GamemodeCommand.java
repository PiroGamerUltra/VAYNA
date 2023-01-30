package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.valorantapi.ValorantAPI;
import dev.piste.vayna.config.translations.Language;
import dev.piste.vayna.manager.Command;
import dev.piste.vayna.apis.valorantapi.gson.Gamemode;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class GamemodeCommand implements Command {

    @Override
    public void perform(SlashCommandInteractionEvent event) throws StatusCodeException {
        event.deferReply().queue();

        Language language = Language.getLanguage(event.getGuild());

        String uuid = ValorantAPI.getGamemodeByName(event.getOption("name").getAsString(), "en-US").getUuid();
        Gamemode gamemode = ValorantAPI.getGamemode(uuid, language.getLanguageCode());

        Embed embed = new Embed();
        embed.setAuthor(event.getUser().getName(), Configs.getSettings().getWebsiteUri(), event.getUser().getAvatarUrl());
        embed.setTitle("» " + gamemode.getDisplayName());
        embed.addField(language.getCommands().getGamemode().getDuration(), gamemode.getDuration(), true);
        embed.setThumbnail(gamemode.getDisplayIcon());
        event.getHook().editOriginalEmbeds(embed.build()).queue();
    }

    @Override
    public void register() throws StatusCodeException {
        OptionData optionData = new OptionData(OptionType.STRING, "name", "Name of the gamemode", true);
        for(Gamemode gamemode : ValorantAPI.getGamemodes("en-US")) {
            if(gamemode.getDisplayName().equalsIgnoreCase("Onboarding") || gamemode.getDisplayName().equalsIgnoreCase("PRACTICE")) continue;
            optionData.addChoice(gamemode.getDisplayName(), gamemode.getDisplayName());
        }
        Bot.getJDA().upsertCommand(getName(), getDescription()).addOptions(optionData).queue();
    }

    @Override
    public String getName() {
        return "gamemode";
    }

    @Override
    public String getDescription() {
        return "Get information about a specific VALORANT gamemode";
    }

}
