package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.riotgames.RiotAPI;
import dev.piste.vayna.counter.StatsCounter;
import dev.piste.vayna.manager.Command;
import dev.piste.vayna.mongodb.AuthKey;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.buttons.Buttons;
import dev.piste.vayna.util.messages.ReplyMessages;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

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
    public void register() {
        Bot.getJDA().upsertCommand(getName(), getDescription()).queue();
    }

    @Override
    public String getName() {
        return "connection";
    }

    @Override
    public String getDescription() {
        return "Manage the connection to your Riot-Games account and its visibility";
    }
}
