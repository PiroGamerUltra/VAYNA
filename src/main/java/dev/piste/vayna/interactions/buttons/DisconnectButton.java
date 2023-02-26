package dev.piste.vayna.interactions.buttons;

import dev.piste.vayna.util.StatsCounter;
import dev.piste.vayna.interactions.managers.Button;
import dev.piste.vayna.mongodb.AuthKey;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.MessageEmbeds;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class DisconnectButton implements Button {

    public void perform(ButtonInteractionEvent event, String[] args) {
        Language language = LanguageManager.getLanguage(event.getGuild());
        LinkedAccount linkedAccount = new LinkedAccount(event.getUser().getIdLong());
        if(linkedAccount.isExisting()) linkedAccount.delete();

        // Reply
        event.editMessageEmbeds(MessageEmbeds.getNoConnectionEmbed(language, event.getUser())).setActionRow(
                Buttons.getConnectButton(event.getGuild(), new AuthKey(event.getUser().getIdLong()).getAuthKey())
        ).queue();

        StatsCounter.countConnections();
    }

    @Override
    public String getName() {
        return "disconnect";
    }

}
