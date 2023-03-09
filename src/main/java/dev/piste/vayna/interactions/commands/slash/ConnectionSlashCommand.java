package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.interactions.ConnectionInteraction;
import dev.piste.vayna.interactions.util.interfaces.ISlashCommand;
import dev.piste.vayna.util.StatsCounter;
import dev.piste.vayna.mongodb.RSOConnection;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.translations.LanguageManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class ConnectionSlashCommand implements ISlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event, Language language) throws HttpErrorException, IOException, InterruptedException {
        event.deferReply(true).queue();

        RSOConnection rsoConnection = new RSOConnection(event.getUser().getIdLong());
        if(!rsoConnection.isExisting()) {
            ConnectionInteraction.sendConnectionMissingMessage(event.getHook(), language);
        } else {
            ConnectionInteraction.sendConnectionPresentMessage(rsoConnection, event.getHook(), language);
        }

        StatsCounter.countConnections();
    }

    @Override
    public String getName() {
        return "connection";
    }

    @Override
    public String getDescription(Language language) {
        return language.getTranslation("command-connection-desc");
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(getName(), getDescription(LanguageManager.getDefaultLanguage()));
    }
}