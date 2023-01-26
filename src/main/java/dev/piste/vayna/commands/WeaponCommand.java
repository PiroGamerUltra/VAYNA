package dev.piste.vayna.commands;

import dev.piste.vayna.api.valorantapi.Weapon;
import dev.piste.vayna.api.valorantapi.weapon.DamageRanges;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class WeaponCommand {

    public static void performCommand(SlashCommandInteractionEvent event) {

        Weapon weapon = Weapon.getWeaponByName(event.getOption("name").getAsString());



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

}
