package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.officer.OfficerAPI;
import dev.piste.vayna.apis.officer.gson.Agent;
import dev.piste.vayna.apis.officer.gson.agent.Ability;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class AgentSlashCommand implements SlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event) throws HttpErrorException, IOException, InterruptedException {
        event.deferReply().setEphemeral(true).queue();
        Language language = LanguageManager.getLanguage(event.getGuild());

        // Searching the agent by the provided UUID
        Agent agent = new OfficerAPI().getAgent(event.getOption("name").getAsString(), language.getLanguageCode());

        // Creating the list of embeds to be sent
        ArrayList<MessageEmbed> embedList = new ArrayList<>();

        // Adding the agent embed (general information)
        Embed agentEmbed = new Embed()
                .setAuthor(agent.getDisplayName(), agent.getDisplayIcon())
                .setDescription(">>> " + agent.getDescription())
                .setThumbnail(agent.getFullPortrait())
                .removeFooter();
        embedList.add(agentEmbed.build());

        // Adding the role embed
        Embed roleEmbed = new Embed()
                .setAuthor(agent.getRole().getDisplayName(), agent.getRole().getDisplayIcon())
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
            Embed abilityEmbed = new Embed()
                    .setAuthor(ability.getDisplayName() + " (" + abilityKey + ")", ability.getDisplayIcon())
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
        return LanguageManager.getLanguage().getTranslation("command-agent-description");
    }

    @Override
    public CommandData getCommandData() throws HttpErrorException, IOException, InterruptedException {
        OptionData optionData = new OptionData(OptionType.STRING, "name", "Agent name", true);
        for(Agent agent : new OfficerAPI().getAgents("en-US")) {
            optionData.addChoice(agent.getDisplayName(), agent.getUuid());
        }
        return Commands.slash(getName(), getDescription()).addOptions(optionData);
    }
}
