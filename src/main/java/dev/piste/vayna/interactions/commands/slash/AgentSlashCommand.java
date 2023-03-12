package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.http.HttpErrorException;
import dev.piste.vayna.http.apis.OfficerAPI;
import dev.piste.vayna.http.models.officer.Agent;
import dev.piste.vayna.interactions.util.interfaces.ISlashCommand;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.translations.LanguageManager;
import dev.piste.vayna.util.Embed;
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
public class AgentSlashCommand implements ISlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event, Language language) throws HttpErrorException, IOException, InterruptedException {
        event.deferReply(true).queue();

        Agent agent = new OfficerAPI().getAgent(event.getOption("name").getAsString(), language);
        ArrayList<MessageEmbed> embedList = new ArrayList<>();

        Embed agentEmbed = new Embed()
                .setAuthor(agent.getDisplayName(), agent.getDisplayIcon())
                .setDescription(">>> " + agent.getDescription())
                .setThumbnail(agent.getFullPortrait())
                .removeFooter();
        embedList.add(agentEmbed.build());

        Embed roleEmbed = new Embed()
                .setAuthor(agent.getRole().getDisplayName(), agent.getRole().getDisplayIcon())
                .setDescription(">>> " + agent.getRole().getDescription())
                .removeFooter();
        embedList.add(roleEmbed.build());

        for(Agent.Ability ability : agent.getAbilities()) {
            if(ability.getSlotKey().equalsIgnoreCase("Passive")) continue;
            Embed abilityEmbed = new Embed()
                    .setAuthor(ability.getDisplayName() + " (" + ability.getSlotKey() + ")", ability.getDisplayIcon())
                    .setDescription(">>> " + ability.getDescription())
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
    public String getDescription(Language language) {
        return language.getTranslation("command-agent-desc");
    }

    @Override
    public CommandData getCommandData() throws HttpErrorException, IOException, InterruptedException {
        OptionData optionData = new OptionData(OptionType.STRING, "name", "The name of the agent", true);
        for(Agent agent : new OfficerAPI().getAgents(LanguageManager.getDefaultLanguage())) {
            optionData.addChoice(agent.getDisplayName(), agent.getId());
        }
        return Commands.slash(getName(), getDescription(LanguageManager.getDefaultLanguage())).addOptions(optionData);
    }
}