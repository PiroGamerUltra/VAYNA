package dev.piste.vayna.interactions.selectmenus.string;

import dev.piste.vayna.apis.HenrikAPI;
import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.entities.henrik.StoreBundle;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.translations.LanguageManager;
import dev.piste.vayna.util.templates.MessageEmbeds;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class BundleSelectMenu implements IStringSelectMenu {


    @Override
    public void perform(StringSelectInteractionEvent event, Language language) throws HttpErrorException, IOException, InterruptedException {
        String bundleUuid = event.getValues().get(0);

        for(StoreBundle storeBundle : new HenrikAPI().getStoreBundles()) {
            if(storeBundle.getId().toLowerCase().equals(bundleUuid)) {
                event.editMessageEmbeds(MessageEmbeds.getBundleEmbeds(LanguageManager.getLanguage(event.getGuild()), storeBundle)).queue();
            }
        }
    }

    @Override
    public String getName() {
        return "bundleSelect";
    }
}
