package dev.piste.vayna.interactions.selectmenus.string;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.henrik.HenrikAPI;
import dev.piste.vayna.apis.henrik.gson.CurrentBundle;
import dev.piste.vayna.util.templates.MessageEmbeds;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class BundleSelectMenu implements StringSelectMenu {


    @Override
    public void perform(StringSelectInteractionEvent event) throws HttpErrorException, IOException, InterruptedException {
        String bundleUuid = event.getValues().get(0);

        for(CurrentBundle currentBundle : new HenrikAPI().getCurrentBundles()) {
            if(currentBundle.getBundleUuid().equals(bundleUuid)) {
                event.editMessageEmbeds(MessageEmbeds.getBundleEmbeds(LanguageManager.getLanguage(event.getGuild()), currentBundle)).queue();
            }
        }
    }

    @Override
    public String getName() {
        return "bundleSelect";
    }
}
