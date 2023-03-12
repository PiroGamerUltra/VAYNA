package dev.piste.vayna.interactions.selectmenus.string;

import dev.piste.vayna.http.apis.HenrikAPI;
import dev.piste.vayna.http.HttpErrorException;
import dev.piste.vayna.http.models.henrik.StoreBundle;
import dev.piste.vayna.interactions.general.StoreInteraction;
import dev.piste.vayna.interactions.util.interfaces.IStringSelectMenu;
import dev.piste.vayna.translations.Language;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class StoreSelectMenu implements IStringSelectMenu {

    @Override
    public void perform(StringSelectInteractionEvent event, Language language) throws HttpErrorException, IOException, InterruptedException {
        event.deferEdit().queue();

        String bundleUuid = event.getValues().get(0);
        for(StoreBundle storeBundle : new HenrikAPI().getStoreBundles()) {
            if(storeBundle.getId().toLowerCase().equals(bundleUuid)) {
                StoreInteraction.sendBundleEmbed(storeBundle, event.getHook(), language);
            }
        }
    }

    @Override
    public String getName() {
        return "store";
    }

}