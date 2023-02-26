package dev.piste.vayna.interactions.commands.context;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.interactions.managers.UserContextCommand;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.ErrorMessages;
import dev.piste.vayna.util.templates.MessageEmbeds;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class StatsContextCommand implements UserContextCommand {

    @Override
    public void perform(UserContextInteractionEvent event) throws HttpErrorException {
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

        RiotAccount riotAccount = new RiotAPI().getAccount(linkedAccount.getRiotPuuid());

        try {
            event.getHook().editOriginalEmbeds(MessageEmbeds.getStatsEmbed(LanguageManager.getLanguage(event.getGuild()), linkedAccount, riotAccount)).queue();
        } catch (HttpErrorException e) {
            if(e.getStatusCode() == 404) {
                event.getHook().editOriginalEmbeds(ErrorMessages.getInvalidRegion(event.getGuild(), event.getUser(), riotAccount)).setActionRow(
                        Buttons.getSupportButton(event.getGuild())
                ).queue();
            } else {
                throw e;
            }
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
