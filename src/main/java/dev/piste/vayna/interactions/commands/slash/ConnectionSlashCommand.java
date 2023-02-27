package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.util.StatsCounter;
import dev.piste.vayna.mongodb.RsoAuthKey;
import dev.piste.vayna.mongodb.RsoConnection;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.MessageEmbeds;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class ConnectionSlashCommand implements SlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event) throws HttpErrorException, IOException, InterruptedException {
        event.deferReply().setEphemeral(true).queue();
        Language language = LanguageManager.getLanguage(event.getGuild());

        RsoConnection rsoConnection = new RsoConnection(event.getUser().getIdLong());
        if(!rsoConnection.isExisting()) {
            RsoAuthKey rsoAuthKey = new RsoAuthKey(event.getUser().getIdLong());
            rsoAuthKey.refreshExpirationDate();
            event.getHook().editOriginalEmbeds(MessageEmbeds.getNoConnectionEmbed(language, event.getUser(), rsoAuthKey.getExpirationDate())).setActionRow(
                    Buttons.getConnectButton(language, rsoAuthKey.getRsoAuthKey())
            ).queue();
        } else {
            event.getHook().editOriginalEmbeds(MessageEmbeds.getPresentConnectionEmbed(language, event.getUser(), new RiotAPI().getAccount(rsoConnection.getRiotPuuid()).getRiotId(), rsoConnection.isPubliclyVisible())).setActionRow(
                    Buttons.getDisconnectButton(language),
                    Buttons.getVisibilityButton(language, rsoConnection.isPubliclyVisible())
            ).queue();
        }

        StatsCounter.countConnections();
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(getName(), getDescription());
    }

    @Override
    public String getName() {
        return "connection";
    }

    @Override
    public String getDescription() {
        return LanguageManager.getLanguage().getTranslation("command-connection-description");
    }
}
