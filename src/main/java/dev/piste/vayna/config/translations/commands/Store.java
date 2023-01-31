package dev.piste.vayna.config.translations.commands;

import dev.piste.vayna.apis.henrik.gson.CurrentBundle;
import dev.piste.vayna.apis.henrik.gson.store.Item;
import dev.piste.vayna.apis.valorantapi.gson.*;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.time.Instant;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Store {

    private String price;
    private String amount;
    private String duration;
    private String fieldText;

    public MessageEmbed getMessageEmbed(User user, Bundle bundle, CurrentBundle currentBundle) {
        return new Embed()
                .setAuthor(user.getName(), Configs.getSettings().getWebsiteUri(), user.getAvatarUrl())
                .setTitle(Configs.getTranslations().getTitlePrefix() + bundle.getDisplayName())
                .addField(price, currentBundle.getPrice() + " " + Emoji.getVP().getFormatted(), true)
                .addField(duration, fieldText.replaceAll("%timestamp%", "<t:" + (Instant.now().getEpochSecond()+currentBundle.getSecondsRemaining()) + ":R>"), true)
                .setImage(bundle.getDisplayIcon())
                .build();
    }

    public MessageEmbed getMessageEmbed(Item item, Buddy buddy) {
        return new Embed()
                .removeFooter()
                .setTitle(Configs.getTranslations().getTitlePrefix() + buddy.getDisplayName())
                .addField(amount, item.getAmount() + "x", true)
                .addField(price, item.getBasePrice() + " " + Emoji.getVP().getFormatted(), true)
                .setThumbnail(buddy.getDisplayIcon())
                .build();
    }

    public MessageEmbed getMessageEmbed(Item item, Playercard playercard) {
        return new Embed()
                .removeFooter()
                .setTitle(Configs.getTranslations().getTitlePrefix() + playercard.getDisplayName())
                .addField(amount, item.getAmount() + "x", true)
                .addField(price, item.getBasePrice() + " " + Emoji.getVP().getFormatted(), true)
                .setThumbnail(playercard.getLargeArt())
                .setImage(playercard.getWideArt())
                .build();
    }

    public MessageEmbed getMessageEmbed(Item item, Spray spray) {
        Embed embed = new Embed()
                .removeFooter()
                .setTitle(Configs.getTranslations().getTitlePrefix() + spray.getDisplayName())
                .addField(amount, item.getAmount() + "x", true)
                .addField(price, item.getBasePrice() + " " + Emoji.getVP().getFormatted(), true);
        if(spray.getAnimationGif() != null) {
            embed.setThumbnail(spray.getAnimationGif());
        } else {
            embed.setThumbnail(spray.getFullTransparentIcon());
        }
        return embed.build();
    }

    public MessageEmbed getMessageEmbed(Item item, Skin skin) {
        return new Embed()
                .removeFooter()
                .setTitle(Configs.getTranslations().getTitlePrefix() + skin.getDisplayName())
                .addField(amount, item.getAmount() + "x", true)
                .addField(price, item.getBasePrice() + " " + Emoji.getVP().getFormatted(), true)
                .setImage(skin.getDisplayIcon())
                .build();
    }

    public MessageEmbed getMessageEmbed(Item item) {
        return new Embed()
                .removeFooter()
                .setTitle(Configs.getTranslations().getTitlePrefix() + item.getName())
                .addField(amount, item.getAmount() + "x", true)
                .addField(price, item.getBasePrice() + " " + Emoji.getVP().getFormatted(), true)
                .setImage(item.getImage())
                .build();
    }



}
