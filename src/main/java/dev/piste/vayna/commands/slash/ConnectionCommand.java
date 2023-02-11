package dev.piste.vayna.commands.slash;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.util.StatsCounter;
import dev.piste.vayna.manager.Command;
import dev.piste.vayna.mongodb.AuthKey;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.translations.LanguageManager;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.ReplyMessages;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/**
 * @author Piste | https://github.com/zPiste
 */
public class ConnectionCommand implements Command {

    @Override
    public void perform(SlashCommandInteractionEvent event) throws StatusCodeException {
        event.deferReply().setEphemeral(true).queue();

        LinkedAccount linkedAccount = new LinkedAccount(event.getUser().getIdLong());
        if(!linkedAccount.isExisting()) {
            event.getHook().editOriginalEmbeds(ReplyMessages.getConnectionNone(event.getGuild(), event.getUser())).setActionRow(
                    Buttons.getConnectButton(event.getGuild(), new AuthKey(event.getUser().getIdLong()).getAuthKey())
            ).queue();
        } else {
            event.getHook().editOriginalEmbeds(ReplyMessages.getConnectionPresent(event.getGuild(), event.getUser(), RiotAPI.getAccountByPuuid(linkedAccount.getRiotPuuid()).getRiotId(), linkedAccount.isVisibleToPublic())).setActionRow(
                    Buttons.getDisconnectButton(event.getGuild()),
                    Buttons.getVisibilityButton(event.getGuild(), linkedAccount.isVisibleToPublic())
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
