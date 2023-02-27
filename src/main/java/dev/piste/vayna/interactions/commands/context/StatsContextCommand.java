package dev.piste.vayna.interactions.commands.context;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.apis.riot.gson.RiotAccount;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.ErrorMessages;
import dev.piste.vayna.util.templates.MessageEmbeds;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class StatsContextCommand implements UserContextCommand {

    @Override
    public void perform(UserContextInteractionEvent event) throws HttpErrorException, IOException, InterruptedException {
        event.deferReply().queue();
        Language language = LanguageManager.getLanguage(event.getGuild());

        LinkedAccount linkedAccount = new LinkedAccount(event.getTarget().getIdLong());
        if(!linkedAccount.isExisting()) {
            if (linkedAccount.getDiscordUserId() == event.getUser().getIdLong()) {
                event.getHook().editOriginalEmbeds(ErrorMessages.getNoConnectionSelf(event.getGuild(), event.getUser())).setActionRow(
                        Buttons.getSupportButton(language)
                ).queue();
            } else {
                event.getHook().editOriginalEmbeds(ErrorMessages.getNoConnection(event.getGuild(), event.getUser(), event.getJDA().getUserById(linkedAccount.getDiscordUserId()).getAsMention())).setActionRow(
                        Buttons.getSupportButton(language)
                ).queue();
            }
            return;
        } else {
            if(!linkedAccount.isPubliclyVisible() && (linkedAccount.getDiscordUserId() != event.getUser().getIdLong())) {
                event.getHook().editOriginalEmbeds(ErrorMessages.getPrivate(event.getGuild(), event.getUser())).setActionRow(
                        Buttons.getSupportButton(language)
                ).queue();
                return;
            }
        }

        RiotAccount riotAccount = new RiotAPI().getAccount(linkedAccount.getRiotPuuid());

        try {
            event.getHook().editOriginalEmbeds(MessageEmbeds.getStatsEmbed(LanguageManager.getLanguage(event.getGuild()), linkedAccount, riotAccount)).queue();
        } catch (HttpErrorException e) {
            if(e.getStatusCode() == 400 || e.getStatusCode() == 404) {
                event.getHook().editOriginalEmbeds(ErrorMessages.getInvalidRegion(event.getGuild(), event.getUser(), riotAccount)).setActionRow(
                        Buttons.getSupportButton(language)
                ).queue();
            } else {
                throw e;
            }
        }
    }

    @Override
    public CommandData getCommandData() {
        return Commands.user(getName());
    }

    @Override
    public String getName() {
        return "Stats";
    }
}
