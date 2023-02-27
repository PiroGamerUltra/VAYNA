package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.util.StatsCounter;
import dev.piste.vayna.mongodb.AuthKey;
import dev.piste.vayna.mongodb.LinkedAccount;
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

        LinkedAccount linkedAccount = new LinkedAccount(event.getUser().getIdLong());
        if(!linkedAccount.isExisting()) {
            event.getHook().editOriginalEmbeds(MessageEmbeds.getNoConnectionEmbed(language, event.getUser())).setActionRow(
                    Buttons.getConnectButton(language, new AuthKey(event.getUser().getIdLong()).getAuthKey())
            ).queue();
        } else {
            event.getHook().editOriginalEmbeds(MessageEmbeds.getPresentConnectionEmbed(language, event.getUser(), new RiotAPI().getAccount(linkedAccount.getRiotPuuid()).getRiotId(), linkedAccount.isVisibleToPublic())).setActionRow(
                    Buttons.getDisconnectButton(language),
                    Buttons.getVisibilityButton(language, linkedAccount.isVisibleToPublic())
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
