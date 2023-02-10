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

        // test

        String uuid = ValorantAPI.getAgentByName(event.getOption("name").getAsString(), "en-US").getUuid();
        Agent agent = ValorantAPI.getAgent(uuid, language.getLanguageCode());

        ArrayList<MessageEmbed> embedList = new ArrayList<>();

        Embed agentEmbed = new Embed().setAuthor(event.getUser().getName(), event.getUser().getAvatarUrl())
                .setTitle(language.getEmbedTitlePrefix() + agent.getDisplayName())
                .setDescription(agent.getDescription())
                .setThumbnail(agent.getDisplayIcon())
                .setImage(agent.getFullPortrait());
        embedList.add(agentEmbed.build());

        Embed roleEmbed = new Embed().setAuthor(language.getTranslation("command-agent-embed-role-author"), agent.getDisplayIcon())
                .setTitle(language.getEmbedTitlePrefix() + agent.getRole().getDisplayName())
                .setDescription(agent.getRole().getDescription())
                .setThumbnail(agent.getRole().getDisplayIcon())
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
            Embed abilityEmbed = new Embed().setTitle(language.getEmbedTitlePrefix() + ability.getDisplayName() + " (" + abilityKey + ")")
                    .setAuthor(language.getTranslation("command-agent-embed-ability-author"), agent.getDisplayIcon())
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
    public void register() throws StatusCodeException {
        OptionData optionData = new OptionData(OptionType.STRING, "name", "Name of the agent", true);
        for(Agent agent : ValorantAPI.getAgents("en-US")) {
            if(!agent.isPlayableCharacter()) continue;
            optionData.addChoice(agent.getDisplayName(), agent.getDisplayName());
        }
        Bot.getJDA().upsertCommand(getName(), getDescription()).addOptions(optionData).queue();
    }
}
