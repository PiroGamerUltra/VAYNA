package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
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
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;

public class AgentCommand implements Command {

    @Override
    public void perform(SlashCommandInteractionEvent event) throws StatusCodeException {
        event.deferReply().setEphemeral(true).queue();
        Language language = LanguageManager.getLanguage(event.getGuild());
        String uuid = ValorantAPI.getAgentByName(event.getOption("name").getAsString(), "en-US").getUuid();
        Agent agent = ValorantAPI.getAgent(uuid, language.getLanguageCode());



        ArrayList<MessageEmbed> embedList = new ArrayList<>();

        Embed agentEmbed = new Embed().setAuthor(agent.getDisplayName(), agent.getDisplayIcon())
                .setDescription(agent.getDescription())
                .setThumbnail(agent.getFullPortrait())
                .removeFooter();
        embedList.add(agentEmbed.build());

        //test

        Embed roleEmbed = new Embed().setAuthor(agent.getRole().getDisplayName(), agent.getRole().getDisplayIcon())
                .setDescription("> " + agent.getRole().getDescription())
                .removeFooter();
        embedList.add(roleEmbed.build());

        for(Ability ability : agent.getAbilities()) {
            if(ability.getSlot().equalsIgnoreCase("Passive")) continue;
            String abilityKey;
            switch (ability.getSlot()) {
                case "Ability1" -> abilityKey = "Q";
                case "Ability2" -> abilityKey = "E";
                case "Grenade" -> abilityKey = "C";
                case "Ultimate" -> abilityKey = "X";
                default -> abilityKey = "Error";
            }
            Embed abilityEmbed = new Embed().setAuthor(ability.getDisplayName() + " (" + abilityKey + ")", ability.getDisplayIcon())
                    .setDescription("> " + ability.getDescription())
                    .removeFooter();
            embedList.add(abilityEmbed.build());
        }

        event.getHook().editOriginalEmbeds(embedList).queue();
    }

    @Override
    public String getName() {
        return "agent";
    }

    @Override
    public String getDescription() {
        return "Retrieve general information about a VALORANT agent";
    }

    @Override
    public void register() throws StatusCodeException {
        OptionData optionData = new OptionData(OptionType.STRING, "name", "Name of the agent", true);
        for(Agent agent : ValorantAPI.getAgents("en-US")) {
            if(!agent.isPlayableCharacter()) continue;
            optionData.addChoice(agent.getDisplayName(), agent.getDisplayName());
        }
        Bot.getJDA().upsertCommand(getName(), getDescription()).addOptions(optionData).queue();
    }
}
