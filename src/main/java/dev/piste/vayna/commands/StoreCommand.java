package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.henrik.HenrikAPI;
import dev.piste.vayna.apis.valorantapi.ValorantAPI;
import dev.piste.vayna.apis.valorantapi.gson.*;
import dev.piste.vayna.config.translations.Language;
import dev.piste.vayna.manager.Command;
import dev.piste.vayna.apis.henrik.gson.CurrentBundle;
import dev.piste.vayna.apis.henrik.gson.store.Item;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.config.settings.SettingsConfig;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class StoreCommand implements Command {


    @Override
    public void perform(SlashCommandInteractionEvent event) throws StatusCodeException {
        event.deferReply().queue();

        Language language = Language.getLanguage(event.getGuild());

        List<MessageEmbed> embedList = new ArrayList<>();

        for(CurrentBundle currentBundle : HenrikAPI.getCurrentBundles()) {
            Bundle bundle = ValorantAPI.getBundle(currentBundle.getBundleUuid(), language.getLanguageCode());

            embedList.add(language.getCommands().getStore().getMessageEmbed(event.getUser(), bundle, currentBundle));

            for(Item item : currentBundle.getItems()) {
                if(item.getType().equalsIgnoreCase("buddy")) {
                    Buddy buddy = ValorantAPI.getBuddy(item.getUuid(), language.getLanguageCode());
                    embedList.add(language.getCommands().getStore().getMessageEmbed(item, buddy));
                } else if(item.getType().equalsIgnoreCase("player_card")) {
                    Playercard playercard = ValorantAPI.getPlayercard(item.getUuid(), language.getLanguageCode());
                    embedList.add(language.getCommands().getStore().getMessageEmbed(item, playercard));
                } else if(item.getType().equalsIgnoreCase("spray")) {
                    Spray spray = ValorantAPI.getSpray(item.getUuid(), language.getLanguageCode());
                    embedList.add(language.getCommands().getStore().getMessageEmbed(item, spray));
                } else if(item.getType().equalsIgnoreCase("skin_level")) {
                    Skin skin = ValorantAPI.getSkin(item.getUuid(), language.getLanguageCode());
                    embedList.add(language.getCommands().getStore().getMessageEmbed(item, skin));
                } else {
                    embedList.add(language.getCommands().getStore().getMessageEmbed(item));
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
