package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.valorantapi.ValorantAPI;
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
    public void perform(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        Weapon weapon = ValorantAPI.getWeaponByName(event.getOption("name").getAsString());

        Embed embed = new Embed();
        embed.setAuthor(event.getUser().getName(), Configs.getSettings().getWebsiteUri(), event.getUser().getAvatarUrl());
        embed.setTitle("» " + weapon.getDisplayName());
        embed.addField("Cost", weapon.getShopData().getCost() + " " + Emoji.getVP().getFormatted(), false);
        embed.addField("Type", weapon.getShopData().getCategoryText() + "", false);
        embed.addField("Magazine", "**" + weapon.getWeaponStats().getMagazineSize() + "** bullets", true);
        embed.addField("Fire rate", "**" + weapon.getWeaponStats().getFireRate() + "** rounds/sec", true);
        embed.addField("Equip time", "**" + weapon.getWeaponStats().getEquipTimeSeconds() + "** seconds", true);
        embed.addField("Reload time", "**" + weapon.getWeaponStats().getReloadTimeSeconds() + "** seconds", true);
        for(DamageRanges damageRanges : weapon.getWeaponStats().getDamageRanges()) {
            embed.addField("Damage (" + damageRanges.getRangeStartMeters() + "-" + damageRanges.getRangeEndMeters() + "m)",
                    "Head - **" + damageRanges.getHeadDamage() + "**\n" +
                            "Body - **" + damageRanges.getBodyDamage() + "**\n" +
                            "Legs - **" + damageRanges.getLegDamage() + "**", false);
        }
        embed.setImage(weapon.getDisplayIcon());
        event.getHook().editOriginalEmbeds(embed.build()).queue();
    }

    @Override
    public void register() {
        OptionData optionData = new OptionData(OptionType.STRING, "name", "Name of the weapon", true);
        for(Weapon weapon : ValorantAPI.getWeapons()) {
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
