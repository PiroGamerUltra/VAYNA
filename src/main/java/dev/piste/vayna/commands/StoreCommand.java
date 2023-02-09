package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.henrik.HenrikAPI;
import dev.piste.vayna.apis.valorantapi.ValorantAPI;
import dev.piste.vayna.apis.valorantapi.gson.*;
import dev.piste.vayna.manager.Command;
import dev.piste.vayna.apis.henrik.gson.CurrentBundle;
import dev.piste.vayna.apis.henrik.gson.store.Item;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import dev.piste.vayna.util.Language;
import dev.piste.vayna.util.LanguageManager;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class StoreCommand implements Command {


    @Override
    public void perform(SlashCommandInteractionEvent event) throws StatusCodeException {
        event.deferReply().queue();

        Language language = LanguageManager.getLanguage(event.getGuild());

        List<MessageEmbed> embedList = new ArrayList<>();

        for(CurrentBundle currentBundle : HenrikAPI.getCurrentBundles()) {
            Bundle bundle = ValorantAPI.getBundle(currentBundle.getBundleUuid(), language.getLanguageCode());

            Embed bundleEmbed = new Embed()
                    .setAuthor(event.getUser().getName(), ConfigManager.getSettingsConfig().getWebsiteUri(), event.getUser().getAvatarUrl())
                    .setTitle(language.getEmbedTitlePrefix() + bundle.getDisplayName())
                    .addField(language.getTranslation("command-store-embed-bundle-field-1-name"), currentBundle.getPrice() + " " + Emoji.getVP().getFormatted(), true)
                    .addField(language.getTranslation("command-store-embed-bundle-field-2-name"), language.getTranslation("command-store-embed-bundle-field-2-text")
                            .replaceAll("%timestamp%", "<t:" + (Instant.now().getEpochSecond()+currentBundle.getSecondsRemaining()) + ":R>"), true)
                    .setImage(bundle.getDisplayIcon());

            embedList.add(bundleEmbed.build());

            for(Item item : currentBundle.getItems()) {
                if(item.getType().equalsIgnoreCase("buddy")) {
                    Buddy buddy = ValorantAPI.getBuddy(item.getUuid(), language.getLanguageCode());
                    Embed embed = new Embed()
                            .removeFooter()
                            .setTitle(language.getEmbedTitlePrefix() + buddy.getDisplayName())
                            .addField(language.getTranslation("command-store-embed-item-field-1-name"), item.getAmount() + "x", true)
                            .addField(language.getTranslation("command-store-embed-item-field-2-name"), item.getBasePrice() + " " + Emoji.getVP().getFormatted(), true)
                            .setThumbnail(buddy.getDisplayIcon());
                    embedList.add(embed.build());
                } else if(item.getType().equalsIgnoreCase("player_card")) {
                    Playercard playercard = ValorantAPI.getPlayercard(item.getUuid(), language.getLanguageCode());
                    Embed embed = new Embed()
                            .removeFooter()
                            .setTitle(language.getEmbedTitlePrefix() + playercard.getDisplayName())
                            .addField(language.getTranslation("command-store-embed-item-field-1-name"), item.getAmount() + "x", true)
                            .addField(language.getTranslation("command-store-embed-item-field-2-name"), item.getBasePrice() + " " + Emoji.getVP().getFormatted(), true)
                            .setThumbnail(playercard.getLargeArt())
                            .setImage(playercard.getWideArt());
                    embedList.add(embed.build());
                } else if(item.getType().equalsIgnoreCase("spray")) {
                    Spray spray = ValorantAPI.getSpray(item.getUuid(), language.getLanguageCode());
                    Embed embed = new Embed()
                            .removeFooter()
                            .setTitle(language.getEmbedTitlePrefix() + spray.getDisplayName())
                            .addField(language.getTranslation("command-store-embed-item-field-1-name"), item.getAmount() + "x", true)
                            .addField(language.getTranslation("command-store-embed-item-field-2-name"), item.getBasePrice() + " " + Emoji.getVP().getFormatted(), true);
                    if(spray.getAnimationGif() != null) {
                        embed.setThumbnail(spray.getAnimationGif());
                    } else {
                        embed.setThumbnail(spray.getFullTransparentIcon());
                    }
                    embedList.add(embed.build());
                } else if(item.getType().equalsIgnoreCase("skin_level")) {
                    Skin skin = ValorantAPI.getSkin(item.getUuid(), language.getLanguageCode());
                    Embed embed = new Embed()
                            .removeFooter()
                            .setTitle(language.getEmbedTitlePrefix() + skin.getDisplayName())
                            .addField(language.getTranslation("command-store-embed-item-field-1-name"), item.getAmount() + "x", true)
                            .addField(language.getTranslation("command-store-embed-item-field-2-name"), item.getBasePrice() + " " + Emoji.getVP().getFormatted(), true)
                            .setImage(skin.getDisplayIcon());
                    embedList.add(embed.build());
                } else {
                    Embed embed = new Embed()
                            .removeFooter()
                            .setTitle(language.getEmbedTitlePrefix() + item.getName())
                            .addField(language.getTranslation("command-store-embed-item-field-1-name"), item.getAmount() + "x", true)
                            .addField(language.getTranslation("command-store-embed-item-field-2-name"), item.getBasePrice() + " " + Emoji.getVP().getFormatted(), true)
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
