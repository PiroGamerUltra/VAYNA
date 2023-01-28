package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.manager.Command;
import dev.piste.vayna.api.valorantapi.Gamemode;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class GamemodeCommand implements Command {

    @Override
    public void perform(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        Gamemode gamemode = Gamemode.getGamemodeByName(event.getOption("name").getAsString());

        Embed embed = new Embed();
        embed.setAuthor(event.getUser().getName(), Configs.getSettings().getWebsiteUri(), event.getUser().getAvatarUrl());
        embed.setTitle("» " + gamemode.getDisplayName());
        embed.addField("Average duration", gamemode.getDuration(), true);
        embed.setThumbnail(gamemode.getDisplayIcon());
        event.getHook().editOriginalEmbeds(embed.build()).queue();
    }

    @Override
    public void register() {
        OptionData optionData = new OptionData(OptionType.STRING, "name", "Name of the gamemode", true);
        for(Gamemode gamemode : Gamemode.getGamemodes()) {
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
