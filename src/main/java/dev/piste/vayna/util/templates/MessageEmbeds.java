package dev.piste.vayna.util.templates;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.henrik.gson.CurrentBundle;
import dev.piste.vayna.apis.henrik.gson.store.Item;
import dev.piste.vayna.apis.officer.OfficerAPI;
import dev.piste.vayna.apis.officer.gson.*;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emojis;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class MessageEmbeds {

    public static MessageEmbed getNoConnectionEmbed(Language language, User user, Date expirationDate) {
        return new Embed()
                .setAuthor(user.getName(), user.getAvatarUrl())
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-connection-none-embed-title"))
                .setDescription(language.getTranslation("command-connection-none-embed-description")
                        .replaceAll("%expirationDate%", "<t:" + Math.round((float) expirationDate.getTime() / 1000) + ":R>"))
                .build();
    }

    public static MessageEmbed getPresentConnectionEmbed(Language language, User user, String riotId, boolean visibleToPublic) {
        return new Embed()
                .setAuthor(user.getName(), user.getAvatarUrl())
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-connection-present-embed-title"))
                .setDescription(language.getTranslation("command-connection-present-embed-description"))
                .addField(language.getTranslation("command-connection-present-embed-field-1-name"),
                        user.getAsMention() + " " + Emojis.getDiscord().getFormatted() + " \uD83D\uDD17 " + Emojis.getRiotGames().getFormatted() + " `" + riotId + "`", true)
                .addField(language.getTranslation("command-connection-present-embed-field-2-name"),
                        visibleToPublic ? "\uD83D\uDD13 " + language.getTranslation("command-connection-present-embed-field-2-text-public")
                                : "\uD83D\uDD12 " + language.getTranslation("command-connection-present-embed-field-2-text-private"), true)
                .build();
    }

    public static MessageEmbed getSettingsEmbed(Language language, Guild guild) {
        return new Embed()
                .setAuthor(guild.getName(), guild.getIconUrl())
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-settings-embed-title"))
                .addField(language.getTranslation("command-settings-embed-field-1-name"),
                        language.getTranslation("language-emoji") + " " + language.getTranslation("language-name"), false)
                .build();
    }

    public static ArrayList<MessageEmbed> getBundleEmbeds(Language language, CurrentBundle currentBundle) throws HttpErrorException, IOException, InterruptedException {
        OfficerAPI officerAPI = new OfficerAPI();
        ArrayList<MessageEmbed> embedList = new ArrayList<>();
        Bundle bundle = officerAPI.getBundle(currentBundle.getBundleUuid(), language.getLanguageCode());

        Embed bundleEmbed = new Embed()
                .setTitle(language.getEmbedTitlePrefix() + bundle.getDisplayName())
                .addField(language.getTranslation("command-store-embed-bundle-field-1-name"), currentBundle.getPrice() + " " + Emojis.getVP().getFormatted(), true)
                .addField(language.getTranslation("command-store-embed-bundle-field-2-name"), language.getTranslation("command-store-embed-bundle-field-2-text")
                        .replaceAll("%timestamp%", "<t:" + (Instant.now().getEpochSecond()+currentBundle.getSecondsRemaining()) + ":R>"), true)
                .setImage(bundle.getDisplayIcon())
                .removeFooter();
        embedList.add(bundleEmbed.build());

        for(Item item : currentBundle.getItems()) {
            Embed itemEmbed = new Embed()
                    .addField(language.getTranslation("command-store-embed-item-field-1-name"), item.getAmount() + "x", true)
                    .addField(language.getTranslation("command-store-embed-item-field-2-name"), item.getBasePrice() + " " + Emojis.getVP().getFormatted(), true)
                    .removeFooter();
            switch (item.getType()) {
                case "buddy" -> {
                    Buddy buddy = officerAPI.getBuddy(item.getUuid(), language.getLanguageCode());
                    itemEmbed.setTitle(language.getEmbedTitlePrefix() + buddy.getDisplayName())
                            .setThumbnail(buddy.getDisplayIcon());
                }
                case "player_card" -> {
                    Playercard playercard = officerAPI.getPlayercard(item.getUuid(), language.getLanguageCode());
                    itemEmbed.setTitle(language.getEmbedTitlePrefix() + playercard.getDisplayName())
                            .setThumbnail(playercard.getLargeArt())
                            .setImage(playercard.getWideArt());
                }
                case "spray" -> {
                    Spray spray = officerAPI.getSpray(item.getUuid(), language.getLanguageCode());
                    itemEmbed.setTitle(language.getEmbedTitlePrefix() + spray.getDisplayName())
                            .setThumbnail(spray.getAnimationGif() != null ? spray.getAnimationGif() : spray.getFullTransparentIcon());
                }
                case "skin_level" -> {
                    Skin skin = officerAPI.getSkin(item.getUuid(), language.getLanguageCode());
                    itemEmbed.setTitle(language.getEmbedTitlePrefix() + skin.getDisplayName())
                            .setImage(skin.getDisplayIcon());
                }
                default -> itemEmbed.setTitle(language.getEmbedTitlePrefix() + item.getName())
                        .setImage(item.getImage());
            }
            embedList.add(itemEmbed.build());
        }
        return embedList;
    }

}