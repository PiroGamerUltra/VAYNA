package dev.piste.vayna.commands.slash;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.officer.OfficerAPI;
import dev.piste.vayna.apis.officer.gson.Queue;
import dev.piste.vayna.commands.manager.SlashCommand;
import dev.piste.vayna.apis.officer.gson.Gamemode;
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
public class GamemodeSlashCommand implements SlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event) throws StatusCodeException {
        event.deferReply().setEphemeral(true).queue();
        Language language = LanguageManager.getLanguage(event.getGuild());

        // Searching the gamemode by the provided UUID
        Queue queue = OfficerAPI.getQueue(event.getOption("name").getAsString(), language.getLanguageCode());
        Gamemode gamemode = getGamemode(queue.getUuid(), language.getLanguageCode());

        // Creating the reply embed
        Embed embed = new Embed()
                .setAuthor(queue.getDropdownText() + (queue.isBeta() ? " " + language.getTranslation("command-gamemode-embed-author") : ""), gamemode.getDisplayIcon())
                .setDescription(">>> " + queue.getDescription())
                .addField(language.getTranslation("command-gamemode-embed-field-1-name"), gamemode.getDuration(), true)
                .removeFooter();

        // Reply
        event.getHook().editOriginalEmbeds(embed.build()).queue();
    }

    @Override
    public CommandData getCommandData() throws StatusCodeException {
        OptionData optionData = new OptionData(OptionType.STRING, "name", "Gamemode name", true);
        for(Queue queue : OfficerAPI.getQueues("en-US")) {
            if(queue.getQueueId().equals("custom") ||queue.getQueueId().equals("newmap")) continue;
            optionData.addChoice(queue.getDropdownText(), queue.getUuid());
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

    private Gamemode getGamemode(String queueUuid, String languageCode) throws StatusCodeException {
        String gamemodeUuid = switch (queueUuid) {
            case "d2faff85-4964-f52e-b6b5-73a5d66ccad6", "63d60a3e-4838-695d-9077-e9af5ed523ca", "6ca8aa97-413c-241b-8927-d5bd1661c1d4", "494b69f1-4e3a-1b03-c2cd-a4875d6e9cb6" -> "96bd3920-4f36-d026-2b28-c683eb0bcac5";
            case "f3126c5e-4a6c-1f02-b216-cb9bf58df856" -> "a8790ec5-4237-f2f0-e93b-08a8e89865b2";
            case "3652def6-48fa-b868-61cc-d29702fc5dfa" -> "a4ed6518-4741-6dcb-35bd-f884aecdc859";
            case "b1983fc3-4594-ce27-cfa8-8eb2a9b93018" -> "4744698a-4513-dc96-9c22-a9aa437e4a58";
            case "154239f3-470c-612c-dd46-7db11282f208" -> "57038d6d-49b1-3a74-c5ef-3395d9f23a97";
            case "3938d5da-43bf-1e8c-f09f-858caf145975" -> "e921d1e6-416b-c31f-1291-74930c330b7b";
            case "2d257217-464c-7c4b-efc6-51a55365d44a" -> "5d0f264b-4ebe-cc63-c147-809e1374484b";
            default -> null;
        };
        return OfficerAPI.getGamemode(gamemodeUuid, languageCode);
    }

}
