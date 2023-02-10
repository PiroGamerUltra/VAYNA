package dev.piste.vayna.commands;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.valorantapi.ValorantAPI;
import dev.piste.vayna.apis.valorantapi.gson.Agent;
import dev.piste.vayna.apis.valorantapi.gson.agent.Ability;
import dev.piste.vayna.manager.Command;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Language;
import dev.piste.vayna.util.LanguageManager;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;

public class AgentCommand implements Command {

    @Override
    public void perform(SlashCommandInteractionEvent event) throws StatusCodeException {
        event.deferReply().setEphemeral(true).queue();
        Language language = LanguageManager.getLanguage(event.getGuild());

        // Searching the agent (first on english, then in the guild language)
        Agent agent = ValorantAPI.getAgent(event.getOption("name").getAsString(), language.getLanguageCode());

        // Creating the list of embeds to be sent
        ArrayList<MessageEmbed> embedList = new ArrayList<>();

        // Adding the agent embed (general information)
        Embed agentEmbed = new Embed().setAuthor(agent.getDisplayName(), agent.getDisplayIcon())
                .setDescription(">>> " + agent.getDescription())
                .setThumbnail(agent.getFullPortrait())
                .removeFooter();
        embedList.add(agentEmbed.build());

        // Adding the role embed
        Embed roleEmbed = new Embed().setAuthor(agent.getRole().getDisplayName(), agent.getRole().getDisplayIcon())
                .setDescription(">>> " + agent.getRole().getDescription())
                .removeFooter();
        embedList.add(roleEmbed.build());

        for(Ability ability : agent.getAbilities()) {
            // Skip if the ability name is "Passive"
            if(ability.getSlot().equalsIgnoreCase("Passive")) continue;
            // Determine the hotkeys for the different ability types
            String abilityKey;
            switch (ability.getSlot()) {
                case "Ability1" -> abilityKey = "Q";
                case "Ability2" -> abilityKey = "E";
                case "Grenade" -> abilityKey = "C";
                case "Ultimate" -> abilityKey = "X";
                default -> abilityKey = "Error";
            }
            // Adding the ability embed
            Embed abilityEmbed = new Embed().setAuthor(ability.getDisplayName() + " (" + abilityKey + ")", ability.getDisplayIcon())
                    .setDescription(">>> " + ability.getDescription())
                    .removeFooter();
            embedList.add(abilityEmbed.build());
        }

        // Reply with the embed list
        event.getHook().editOriginalEmbeds(embedList).queue();
    }

    @Override
    public String getName() {
        return "agent";
    }

    @Override
    public String getDescription() {
        return "Get general information about an agent";
    }

    @Override
    public CommandData getCommandData() throws StatusCodeException {
        OptionData optionData = new OptionData(OptionType.STRING, "name", "Agent name", true);
        for(Agent agent : ValorantAPI.getAgents("en-US")) {
            optionData.addChoice(agent.getDisplayName(), agent.getUuid());
        }
        return Commands.slash(getName(), getDescription()).addOptions(optionData);
    }
}
