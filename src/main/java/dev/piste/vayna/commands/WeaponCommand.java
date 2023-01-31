package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.valorantapi.ValorantAPI;
import dev.piste.vayna.config.translations.Language;
import dev.piste.vayna.manager.Command;
import dev.piste.vayna.apis.valorantapi.gson.Weapon;
import dev.piste.vayna.apis.valorantapi.gson.weapon.DamageRanges;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class WeaponCommand implements Command {

    @Override
    public void perform(SlashCommandInteractionEvent event) throws StatusCodeException {
        event.deferReply().queue();

        Language language = Language.getLanguage(event.getGuild());

        String uuid = ValorantAPI.getWeaponByName(event.getOption("name").getAsString(), "en-US").getUuid();
        Weapon weapon = ValorantAPI.getWeapon(uuid, language.getLanguageCode());

        event.getHook().editOriginalEmbeds(language.getCommands().getWeapon().getMessageEmbed(event.getUser(), weapon)).queue();
    }

    @Override
    public void register() throws StatusCodeException {
        OptionData optionData = new OptionData(OptionType.STRING, "name", "Name of the weapon", true);
        for(Weapon weapon : ValorantAPI.getWeapons("en-US")) {
            optionData.addChoice(weapon.getDisplayName(), weapon.getDisplayName());
        }
        Bot.getJDA().upsertCommand(getName(), getDescription()).addOptions(optionData).queue();
    }

    @Override
    public String getName() {
        return "weapon";
    }

    @Override
    public String getDescription() {
        return "Get information about a specific VALORANT weapon";
    }
}
