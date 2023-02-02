package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.henrik.HenrikAPI;
import dev.piste.vayna.apis.valorantapi.ValorantAPI;
import dev.piste.vayna.apis.valorantapi.gson.*;
import dev.piste.vayna.manager.Command;
import dev.piste.vayna.apis.henrik.gson.CurrentBundle;
import dev.piste.vayna.apis.henrik.gson.store.Item;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import dev.piste.vayna.util.TranslationManager;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class StoreCommand implements Command {


    @Override
    public void perform(SlashCommandInteractionEvent event) throws StatusCodeException {
        event.deferReply().queue();

        TranslationManager translation = TranslationManager.getTranslation(event.getGuild());

        List<MessageEmbed> embedList = new ArrayList<>();

        for(CurrentBundle currentBundle : HenrikAPI.getCurrentBundles()) {
            Bundle bundle = ValorantAPI.getBundle(currentBundle.getBundleUuid(), translation.getLanguageCode());

            Embed bundleEmbed = new Embed()
                    .setAuthor(event.getUser().getName(), Configs.getSettings().getWebsiteUri(), event.getUser().getAvatarUrl())
                    .setTitle(translation.getTranslation("embed-title-prefix") + bundle.getDisplayName())
                    .addField(translation.getTranslation("command-store-embed-bundle-field-1-name"), currentBundle.getPrice() + " " + Emoji.getVP().getFormatted(), true)
                    .addField(translation.getTranslation("command-store-embed-bundle-field-2-name"), translation.getTranslation("command-store-embed-bundle-field-2-text")
                            .replaceAll("%timestamp%", "<t:" + (Instant.now().getEpochSecond()+currentBundle.getSecondsRemaining()) + ":R>"), true)
                    .setImage(bundle.getDisplayIcon());

            embedList.add(bundleEmbed.build());

            for(Item item : currentBundle.getItems()) {
                if(item.getType().equalsIgnoreCase("buddy")) {
                    Buddy buddy = ValorantAPI.getBuddy(item.getUuid(), translation.getLanguageCode());
                    Embed embed = new Embed()
                            .removeFooter()
                            .setTitle(translation.getTranslation("embed-title-prefix") + buddy.getDisplayName())
                            .addField(translation.getTranslation("command-store-embed-item-field-1-name"), item.getAmount() + "x", true)
                            .addField(translation.getTranslation("command-store-embed-item-field-2-name"), item.getBasePrice() + " " + Emoji.getVP().getFormatted(), true)
                            .setThumbnail(buddy.getDisplayIcon());
                    embedList.add(embed.build());
                } else if(item.getType().equalsIgnoreCase("player_card")) {
                    Playercard playercard = ValorantAPI.getPlayercard(item.getUuid(), translation.getLanguageCode());
                    Embed embed = new Embed()
                            .removeFooter()
                            .setTitle(translation.getTranslation("embed-title-prefix") + playercard.getDisplayName())
                            .addField(translation.getTranslation("command-store-embed-item-field-1-name"), item.getAmount() + "x", true)
                            .addField(translation.getTranslation("command-store-embed-item-field-2-name"), item.getBasePrice() + " " + Emoji.getVP().getFormatted(), true)
                            .setThumbnail(playercard.getLargeArt())
                            .setImage(playercard.getWideArt());
                    embedList.add(embed.build());
                } else if(item.getType().equalsIgnoreCase("spray")) {
                    Spray spray = ValorantAPI.getSpray(item.getUuid(), translation.getLanguageCode());
                    Embed embed = new Embed()
                            .removeFooter()
                            .setTitle(translation.getTranslation("embed-title-prefix") + spray.getDisplayName())
                            .addField(translation.getTranslation("command-store-embed-item-field-1-name"), item.getAmount() + "x", true)
                            .addField(translation.getTranslation("command-store-embed-item-field-2-name"), item.getBasePrice() + " " + Emoji.getVP().getFormatted(), true);
                    if(spray.getAnimationGif() != null) {
                        embed.setThumbnail(spray.getAnimationGif());
                    } else {
                        embed.setThumbnail(spray.getFullTransparentIcon());
                    }
                    embedList.add(embed.build());
                } else if(item.getType().equalsIgnoreCase("skin_level")) {
                    Skin skin = ValorantAPI.getSkin(item.getUuid(), translation.getLanguageCode());
                    Embed embed = new Embed()
                            .removeFooter()
                            .setTitle(translation.getTranslation("embed-title-prefix") + skin.getDisplayName())
                            .addField(translation.getTranslation("command-store-embed-item-field-1-name"), item.getAmount() + "x", true)
                            .addField(translation.getTranslation("command-store-embed-item-field-2-name"), item.getBasePrice() + " " + Emoji.getVP().getFormatted(), true)
                            .setImage(skin.getDisplayIcon());
                    embedList.add(embed.build());
                } else {
                    Embed embed = new Embed()
                            .removeFooter()
                            .setTitle(translation.getTranslation("embed-title-prefix") + item.getName())
                            .addField(translation.getTranslation("command-store-embed-item-field-1-name"), item.getAmount() + "x", true)
                            .addField(translation.getTranslation("command-store-embed-item-field-2-name"), item.getBasePrice() + " " + Emoji.getVP().getFormatted(), true)
                            .setImage(item.getImage());
                    embedList.add(embed.build());
                }

            }

        }

        event.getHook().editOriginalEmbeds(embedList).queue();

    }

    @Override
    public void register() {
        Bot.getJDA().upsertCommand(getName(), getDescription()).queue();
    }

    @Override
    public String getName() {
        return "store";
    }

    @Override
    public String getDescription() {
        return "Get information about the current bundle in the VALORANT store";
    }
}
