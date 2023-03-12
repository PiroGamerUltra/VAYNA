package dev.piste.vayna.interactions.general;

import dev.piste.vayna.http.HttpErrorException;
import dev.piste.vayna.http.apis.OfficerAPI;
import dev.piste.vayna.http.models.henrik.StoreBundle;
import dev.piste.vayna.http.models.officer.*;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.DiscordEmoji;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class StoreInteraction {

    public static void sendBundleEmbed(StoreBundle storeBundle, InteractionHook hook, Language language) throws IOException, HttpErrorException, InterruptedException {
        OfficerAPI officerAPI = new OfficerAPI();
        ArrayList<MessageEmbed> embedList = new ArrayList<>();
        Bundle bundle = officerAPI.getBundle(storeBundle.getId(), language);

        Embed bundleEmbed = new Embed()
                .setTitle(language.getEmbedTitlePrefix() + bundle.getDisplayName())
                .addField(language.getTranslation("store-embed-bundle-field-1-name"), storeBundle.getPrice() + " " + DiscordEmoji.VP.getAsDiscordEmoji().getFormatted(), true)
                .addField(language.getTranslation("store-embed-bundle-field-2-name"), language.getTranslation("store-embed-bundle-field-2-text")
                        .replaceAll("%timestamp%", "<t:" + storeBundle.getRemovalDate().getTime() / 1000 + ":R>"), true)
                .setImage(bundle.getDisplayIcon())
                .removeFooter();
        embedList.add(bundleEmbed.build());

        for(StoreBundle.Item item : storeBundle.getItems()) {
            Embed itemEmbed = new Embed()
                    .addField(language.getTranslation("store-embed-item-field-1-name"), item.getAmount() + "x", true)
                    .addField(language.getTranslation("store-embed-item-field-2-name"), item.getBasePrice() + " " + DiscordEmoji.VP.getAsDiscordEmoji().getFormatted(), true)
                    .removeFooter();
            switch (item.getType()) {
                case "buddy" -> {
                    Buddy buddy = officerAPI.getBuddy(item.getId(), language);
                    itemEmbed.setTitle(language.getEmbedTitlePrefix() + buddy.getDisplayName())
                            .setThumbnail(buddy.getDisplayIcon());
                }
                case "player_card" -> {
                    PlayerCard playercard = officerAPI.getPlayerCard(item.getId(), language);
                    itemEmbed.setTitle(language.getEmbedTitlePrefix() + playercard.getDisplayName())
                            .setThumbnail(playercard.getLargeArt())
                            .setImage(playercard.getWideArt());
                }
                case "spray" -> {
                    Spray spray = officerAPI.getSpray(item.getId(), language);
                    itemEmbed.setTitle(language.getEmbedTitlePrefix() + spray.getDisplayName())
                            .setThumbnail(spray.getAnimationGif() != null ? spray.getAnimationGif() : spray.getFullTransparentIcon());
                }
                case "skin_level" -> {
                    Weapon.Skin skin = officerAPI.getSkin(item.getId(), language);
                    itemEmbed.setTitle(language.getEmbedTitlePrefix() + skin.getDisplayName())
                            .setImage(skin.getDisplayIcon());
                }
                default -> itemEmbed.setTitle(language.getEmbedTitlePrefix() + item.getDisplayName())
                        .setImage(item.getDisplayIcon());
            }
            embedList.add(itemEmbed.build());
        }

        hook.editOriginalEmbeds(embedList).queue();
    }

}