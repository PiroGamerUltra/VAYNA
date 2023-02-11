package dev.piste.vayna.commands.slash;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.henrik.HenrikAPI;
import dev.piste.vayna.apis.valorantapi.ValorantAPI;
import dev.piste.vayna.apis.valorantapi.gson.*;
import dev.piste.vayna.manager.Command;
import dev.piste.vayna.apis.henrik.gson.CurrentBundle;
import dev.piste.vayna.apis.henrik.gson.store.Item;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Piste | https://github.com/zPiste
 */
public class StoreCommand implements Command {


    @Override
    public void perform(SlashCommandInteractionEvent event) throws StatusCodeException {
        event.deferReply().setEphemeral(true).queue();
        Language language = LanguageManager.getLanguage(event.getGuild());

        // Creating the list of embeds to be sent
        List<MessageEmbed> embedList = new ArrayList<>();

        for(CurrentBundle currentBundle : HenrikAPI.getCurrentBundles()) {
            // Searching the bundle by the UUID of the Henrik CurrentBundle
            Bundle bundle = ValorantAPI.getBundle(currentBundle.getBundleUuid(), language.getLanguageCode());

            // Adding the bundle embed (general information)
            Embed bundleEmbed = new Embed()
                    .setTitle(language.getEmbedTitlePrefix() + bundle.getDisplayName())
                    .addField(language.getTranslation("command-store-embed-bundle-field-1-name"), currentBundle.getPrice() + " " + Emoji.getVP().getFormatted(), true)
                    .addField(language.getTranslation("command-store-embed-bundle-field-2-name"), language.getTranslation("command-store-embed-bundle-field-2-text")
                            .replaceAll("%timestamp%", "<t:" + (Instant.now().getEpochSecond()+currentBundle.getSecondsRemaining()) + ":R>"), true)
                    .setImage(bundle.getDisplayIcon())
                    .removeFooter();
            embedList.add(bundleEmbed.build());

            for(Item item : currentBundle.getItems()) {
                Embed itemEmbed = new Embed()
                        .addField(language.getTranslation("command-store-embed-item-field-1-name"), item.getAmount() + "x", true)
                        .addField(language.getTranslation("command-store-embed-item-field-2-name"), item.getBasePrice() + " " + Emoji.getVP().getFormatted(), true)
                        .removeFooter();
                switch (item.getType()) {
                    case "buddy" -> {
                        Buddy buddy = ValorantAPI.getBuddy(item.getUuid(), language.getLanguageCode());
                        itemEmbed.setTitle(language.getEmbedTitlePrefix() + buddy.getDisplayName())
                                .setThumbnail(buddy.getDisplayIcon());
                    }
                    case "player_card" -> {
                        Playercard playercard = ValorantAPI.getPlayercard(item.getUuid(), language.getLanguageCode());
                        itemEmbed.setTitle(language.getEmbedTitlePrefix() + playercard.getDisplayName())
                                .setThumbnail(playercard.getLargeArt())
                                .setImage(playercard.getWideArt());
                    }
                    case "spray" -> {
                        Spray spray = ValorantAPI.getSpray(item.getUuid(), language.getLanguageCode());
                        itemEmbed.setTitle(language.getEmbedTitlePrefix() + spray.getDisplayName())
                                .setThumbnail(spray.getAnimationGif() != null ? spray.getAnimationGif() : spray.getFullTransparentIcon());
                    }
                    case "skin_level" -> {
                        Skin skin = ValorantAPI.getSkin(item.getUuid(), language.getLanguageCode());
                        itemEmbed.setTitle(language.getEmbedTitlePrefix() + skin.getDisplayName())
                                .setImage(skin.getDisplayIcon());
                    }
                    default -> itemEmbed.setTitle(language.getEmbedTitlePrefix() + item.getName())
                            .setImage(item.getImage());
                }
                // Adding the item embed
                embedList.add(itemEmbed.build());
            }

        }
        // Reply
        event.getHook().editOriginalEmbeds(embedList).queue();
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(getName(), getDescription());
    }

    @Override
    public String getName() {
        return "store";
    }

    @Override
    public String getDescription() {
        return LanguageManager.getLanguage().getTranslation("command-store-description");
    }
}
