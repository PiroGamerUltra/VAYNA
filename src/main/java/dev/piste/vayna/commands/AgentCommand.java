package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.manager.Command;
import dev.piste.vayna.api.valorantapi.Agent;
import dev.piste.vayna.api.valorantapi.agent.Ability;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.config.settings.SettingsConfig;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class AgentCommand implements Command {

    @Override
    public void perform(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        SettingsConfig settingsConfig = Configs.getSettings();
        Agent agent = Agent.getAgentByName(event.getOption("name").getAsString());

        Embed agentEmbed = new Embed();
        agentEmbed.setAuthor(event.getUser().getName(), settingsConfig.getWebsiteUri(), event.getUser().getAvatarUrl())
                .setTitle("» " + agent.getDisplayName())
                .setDescription(agent.getDescription())
                .setThumbnail(agent.getDisplayIcon())
                .setImage(agent.getFullPortrait());

        Embed roleEmbed = new Embed();
        roleEmbed.setAuthor("Role", settingsConfig.getWebsiteUri(), agent.getDisplayIcon())
                .setTitle("» " + agent.getRole().getDisplayName())
                .setDescription(agent.getRole().getDescription())
                .setThumbnail(agent.getRole().getDisplayIcon())
                .removeFooter();

        List<MessageEmbed> embedList = new ArrayList<>();

        embedList.add(agentEmbed.build());
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
            Embed abilityEmbed = new Embed();
            abilityEmbed.setTitle("» " + ability.getDisplayName() + " (" + abilityKey + ")")
                    .setAuthor("Ability", settingsConfig.getWebsiteUri(), agent.getDisplayIcon())
                    .setDescription(ability.getDescription())
                    .setThumbnail(ability.getDisplayIcon())
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
    public void register() {
        OptionData optionData = new OptionData(OptionType.STRING, "name", "Name of the agent", true);
        for(Agent agent : Agent.getAgents()) {
            if(!agent.isPlayableCharacter()) continue;
            optionData.addChoice(agent.getDisplayName(), agent.getDisplayName());
        }
        Bot.getJDA().upsertCommand(getName(), getDescription()).addOptions(optionData).queue();
    }
}
