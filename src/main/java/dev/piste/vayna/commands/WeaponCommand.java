package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.valorantapi.ValorantAPI;
import dev.piste.vayna.manager.Command;
import dev.piste.vayna.apis.valorantapi.gson.Weapon;
import dev.piste.vayna.apis.valorantapi.gson.weapon.DamageRanges;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.TranslationManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class WeaponCommand implements Command {

    @Override
    public void perform(SlashCommandInteractionEvent event) throws StatusCodeException {
        event.deferReply().queue();

        TranslationManager translation = TranslationManager.getTranslation(event.getGuild());

        String uuid = ValorantAPI.getWeaponByName(event.getOption("name").getAsString(), "en-US").getUuid();
        Weapon weapon = ValorantAPI.getWeapon(uuid, translation.getLanguageCode());

        Embed embed = new Embed();
        embed.setAuthor(event.getUser().getName(), Configs.getSettings().getWebsiteUri(), event.getUser().getAvatarUrl());
        embed.setTitle(translation.getTranslation("embed-title-prefix") + weapon.getDisplayName());
        embed.addField(translation.getTranslation("command-weapon-embed-field-1-name"),
                "**" + weapon.getShopData().getCost() + "**", false);
        embed.addField(translation.getTranslation("command-weapon-embed-field-2-name"),
                "**" + weapon.getShopData().getCategoryText() + "**", false);
        embed.addField(translation.getTranslation("command-weapon-embed-field-3-name"),
                "**" + weapon.getWeaponStats().getMagazineSize() + "** " + translation.getTranslation("command-weapon-embed-field-3-text"), true);
        embed.addField(translation.getTranslation("command-weapon-embed-field-4-name"),
                "**" + weapon.getWeaponStats().getFireRate() + "** " + translation.getTranslation("command-weapon-embed-field-4-text"), true);
        embed.addField(translation.getTranslation("command-weapon-embed-field-5-name"),
                "**" + weapon.getWeaponStats().getEquipTimeSeconds() + "** " + translation.getTranslation("command-weapon-embed-field-5-text"), true);
        embed.addField(translation.getTranslation("command-weapon-embed-field-6-name"),
                "**" + weapon.getWeaponStats().getReloadTimeSeconds() + "** " + translation.getTranslation("command-weapon-embed-field-6-text"), true);
        for(DamageRanges damageRanges : weapon.getWeaponStats().getDamageRanges()) {
            embed.addField(translation.getTranslation("command-weapon-embed-field-7-name") + " (" + damageRanges.getRangeStartMeters() + "-" + damageRanges.getRangeEndMeters() + "m)",
                    translation.getTranslation("command-weapon-embed-field-7-text-1") + " - **" + damageRanges.getHeadDamage() + "**\n" +
                            translation.getTranslation("command-weapon-embed-field-7-text-2") + " - **" + damageRanges.getBodyDamage() + "**\n" +
                            translation.getTranslation("command-weapon-embed-field-7-text-3") + " - **" + damageRanges.getLegDamage() + "**", false);
        }
        embed.setImage(weapon.getDisplayIcon());

        event.getHook().editOriginalEmbeds(embed.build()).queue();
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
