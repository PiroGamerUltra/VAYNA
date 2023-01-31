package dev.piste.vayna.buttons;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.riotgames.RiotAPI;
import dev.piste.vayna.apis.riotgames.gson.RiotAccount;
import dev.piste.vayna.config.translations.Language;
import dev.piste.vayna.manager.Button;
import dev.piste.vayna.mongodb.AuthKey;
import dev.piste.vayna.mongodb.LinkedAccount;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class VisibilityButton implements Button {

    public void perform(ButtonInteractionEvent event, String arg) throws StatusCodeException {
        event.deferReply().setEphemeral(true).queue();

        Language language = Language.getLanguage(event.getGuild());

        LinkedAccount linkedAccount = new LinkedAccount(event.getUser().getIdLong());

        if(!linkedAccount.isExisting()) {
            event.getHook().editOriginalEmbeds(language.getCommands().getConnection().getNone().getMessageEmbed(event.getUser())).setActionRow(
                language.getCommands().getConnection().getConnectButton(new AuthKey(event.getUser().getIdLong()).getAuthKey())
            ).queue();
        } else {
            RiotAccount riotAccount = RiotAPI.getAccountByPuuid(linkedAccount.getRiotPuuid());

            boolean visibleToPublic = arg.equalsIgnoreCase("public");

            linkedAccount.update(visibleToPublic);

            event.getHook().editOriginalEmbeds(language.getCommands().getConnection().getPresent().getMessageEmbed(event.getUser(), riotAccount.getRiotId(), visibleToPublic)).setActionRow(
                    language.getCommands().getConnection().getDisconnectButton(),
                    language.getCommands().getConnection().getVisibilityButton(visibleToPublic)
            ).queue();

        }

    }

    @Override
    public String getName() {
        return "connection-visibility;";
    }

}
