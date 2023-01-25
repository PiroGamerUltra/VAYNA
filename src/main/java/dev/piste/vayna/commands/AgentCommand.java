package dev.piste.vayna.commands;

import dev.piste.vayna.api.valorantapi.Agent;
import dev.piste.vayna.api.valorantapi.Map;
import dev.piste.vayna.api.valorantapi.agent.Ability;
import dev.piste.vayna.config.SettingsConfig;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AgentCommand {

    public static void performCommand(SlashCommandInteractionEvent event) {
        Agent agent = Agent.getAgentByName(event.getOption("name").getAsString());

        Embed agentEmbed = new Embed();
        agentEmbed.setAuthor(event.getUser().getName(), SettingsConfig.getWebsiteUri(), event.getUser().getAvatarUrl())
                .setTitle("» " + agent.getDisplayName())
                .setDescription(agent.getDescription())
                .setThumbnail(agent.getDisplayIcon())
                .setImage(agent.getFullPortrait());

        Embed roleEmbed = new Embed();
        roleEmbed.setAuthor("Role", SettingsConfig.getWebsiteUri(), agent.getDisplayIcon())
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
                case "Ability1" -> { abilityKey = "Q";}
                case "Ability2" -> { abilityKey = "E";}
                case "Grenade" -> { abilityKey = "C";}
                case "Ultimate" -> { abilityKey = "X";}
                default -> { abilityKey = "Error";}
            }
            Embed abilityEmbed = new Embed();
            abilityEmbed.setTitle("» " + ability.getDisplayName() + " (" + abilityKey + ")")
                    .setAuthor("Ability", SettingsConfig.getWebsiteUri(), agent.getDisplayIcon())
                    .setDescription(ability.getDescription())
                    .setThumbnail(ability.getDisplayIcon())
                    .removeFooter();
            embedList.add(abilityEmbed.build());
        }

        event.getHook().editOriginalEmbeds(embedList).queue();
    }

}
