package dev.piste.vayna.commands.context;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.riot.InvalidRegionException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.manager.UserContextCommand;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.ErrorMessages;
import dev.piste.vayna.util.templates.ReplyMessages;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/**
 * @author Piste | https://github.com/zPiste
 */
public class StatsContextCommand implements UserContextCommand {

    @Override
    public void perform(UserContextInteractionEvent event) throws StatusCodeException {
        event.deferReply().queue();

        LinkedAccount linkedAccount = new LinkedAccount(event.getTarget().getIdLong());
        if(!linkedAccount.isExisting()) {
            if (linkedAccount.getDiscordUserId() == event.getUser().getIdLong()) {
                event.getHook().editOriginalEmbeds(ErrorMessages.getNoConnectionSelf(event.getGuild(), event.getUser())).setActionRow(
                        Buttons.getSupportButton(event.getGuild())
                ).queue();
            } else {
                event.getHook().editOriginalEmbeds(ErrorMessages.getNoConnection(event.getGuild(), event.getUser(), event.getJDA().getUserById(linkedAccount.getDiscordUserId()).getAsMention())).setActionRow(
                        Buttons.getSupportButton(event.getGuild())
                ).queue();
            }
            return;
        } else {
            if(!linkedAccount.isVisibleToPublic() && (linkedAccount.getDiscordUserId() != event.getUser().getIdLong())) {
                event.getHook().editOriginalEmbeds(ErrorMessages.getPrivate(event.getGuild(), event.getUser())).setActionRow(
                        Buttons.getSupportButton(event.getGuild())
                ).queue();
                return;
            }
        }

        RiotAccount riotAccount = RiotAPI.getAccountByPuuid(linkedAccount.getRiotPuuid());

        try {
            event.getHook().editOriginalEmbeds(ReplyMessages.getStats(event.getGuild(), linkedAccount, riotAccount)).queue();
        } catch (InvalidRegionException e) {
            event.getHook().editOriginalEmbeds(ErrorMessages.getInvalidRegion(event.getGuild(), event.getUser(), riotAccount)).setActionRow(
                    Buttons.getSupportButton(event.getGuild())
            ).queue();
        }
    }

    @Override
    public String getName() {
        return "Stats";
    }

    @Override
    public void register() {
        Bot.getJDA().upsertCommand(Commands.user(getName())).queue();
    }
}
