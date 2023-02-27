package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.henrik.HenrikAPI;
import dev.piste.vayna.apis.henrik.gson.CurrentBundle;
import dev.piste.vayna.apis.officer.OfficerAPI;
import dev.piste.vayna.apis.officer.gson.Bundle;
import dev.piste.vayna.interactions.managers.SlashCommand;
import dev.piste.vayna.interactions.selectmenus.string.BundleSelectMenu;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.templates.MessageEmbeds;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class StoreSlashCommand implements SlashCommand {


    @Override
    public void perform(SlashCommandInteractionEvent event) throws HttpErrorException, IOException, InterruptedException {
        event.deferReply().setEphemeral(true).queue();
        Language language = LanguageManager.getLanguage(event.getGuild());

        OfficerAPI officerAPI = new OfficerAPI();
        HenrikAPI henrikAPI = new HenrikAPI();

        if(henrikAPI.getCurrentBundles().size() <= 1) {
            event.getHook().editOriginalEmbeds(MessageEmbeds.getBundleEmbeds(language, henrikAPI.getCurrentBundles().get(0))).queue();
        } else {
            ArrayList<SelectOption> selectOptions = new ArrayList<>();
            for(CurrentBundle currentBundle : henrikAPI.getCurrentBundles()) {
                Bundle bundle = officerAPI.getBundle(currentBundle.getBundleUuid(), language.getLanguageCode());
                selectOptions.add(SelectOption.of(bundle.getDisplayName(), currentBundle.getBundleUuid()));
            }
            StringSelectMenu stringSelectMenu = StringSelectMenu.create(new BundleSelectMenu().getName())
                    .setPlaceholder(language.getTranslation("command-store-selectmenu-placeholder"))
                    .addOptions(selectOptions)
                    .build();
            Embed embed = new Embed()
                    .setTitle(language.getTranslation("command-store-embed-select-title"))
                    .setDescription(language.getTranslation("command-store-embed-select-description"));
            event.getHook().editOriginalEmbeds(embed.build()).setActionRow(stringSelectMenu).queue();
        }

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
