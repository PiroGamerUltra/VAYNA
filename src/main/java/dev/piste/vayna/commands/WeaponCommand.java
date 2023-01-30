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

        Embed embed = new Embed();
        embed.setAuthor(event.getUser().getName(), Configs.getSettings().getWebsiteUri(), event.getUser().getAvatarUrl());
        embed.setTitle("» " + weapon.getDisplayName());
        embed.addField(language.getCommands().getWeapon().getPrice(), weapon.getShopData().getCost() + " " + Emoji.getVP().getFormatted(), false);
        embed.addField(language.getCommands().getWeapon().getType(), weapon.getShopData().getCategoryText() + "", false);
        embed.addField(language.getCommands().getWeapon().getMagazine(), "**" + weapon.getWeaponStats().getMagazineSize() + "** " + language.getCommands().getWeapon().getBullets(), true);
        embed.addField(language.getCommands().getWeapon().getFireRate(), "**" + weapon.getWeaponStats().getFireRate() + "** " + language.getCommands().getWeapon().getRps(), true);
        embed.addField(language.getCommands().getWeapon().getEquipTime(), "**" + weapon.getWeaponStats().getEquipTimeSeconds() + "** " + language.getCommands().getWeapon().getSeconds(), true);
        embed.addField(language.getCommands().getWeapon().getReloadTime(), "**" + weapon.getWeaponStats().getReloadTimeSeconds() + "** " + language.getCommands().getWeapon().getSeconds(), true);
        for(DamageRanges damageRanges : weapon.getWeaponStats().getDamageRanges()) {
            embed.addField(language.getCommands().getWeapon().getDamage() + " (" + damageRanges.getRangeStartMeters() + "-" + damageRanges.getRangeEndMeters() + "m)",
                    language.getCommands().getWeapon().getHead() + " - **" + damageRanges.getHeadDamage() + "**\n" +
                            language.getCommands().getWeapon().getBody() + " - **" + damageRanges.getBodyDamage() + "**\n" +
                            language.getCommands().getWeapon().getLegs() + " - **" + damageRanges.getLegDamage() + "**", false);
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
