package dev.piste.vayna.buttons;

import dev.piste.vayna.config.translations.Language;
import dev.piste.vayna.counter.StatsCounter;
import dev.piste.vayna.manager.Button;
import dev.piste.vayna.mongodb.AuthKey;
import dev.piste.vayna.mongodb.LinkedAccount;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class DisconnectButton implements Button {

    public void perform(ButtonInteractionEvent event, String arg) {
        event.deferReply().setEphemeral(true).queue();

        Language language = Language.getLanguage(event.getGuild());

        StatsCounter.countConnections();

        LinkedAccount linkedAccount = new LinkedAccount(event.getUser().getIdLong());
        if(linkedAccount.isExisting()) linkedAccount.delete();

        event.getHook().editOriginalEmbeds(language.getCommands().getConnection().getNone().getMessageEmbed(event.getUser())).setActionRow(
            language.getCommands().getConnection().getConnectButton(new AuthKey(event.getUser().getIdLong()).getAuthKey())
        ).queue();
    }

    @Override
    public String getName() {
        return "disconnect;";
    }

}
