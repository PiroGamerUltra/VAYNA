package dev.piste.vayna.config.translations.commands;

import dev.piste.vayna.apis.valorantapi.gson.weapon.DamageRanges;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Weapon {

    private String price;
    private String type;
    private String magazine;
    private String fireRate;
    private String equipTime;
    private String reloadTime;
    private String damage;
    private String head;
    private String body;
    private String legs;
    private String bullets;
    private String rps;
    private String seconds;

    public MessageEmbed getMessageEmbed(User user, dev.piste.vayna.apis.valorantapi.gson.Weapon weapon) {
        Embed embed = new Embed();
        embed.setAuthor(user.getName(), Configs.getSettings().getWebsiteUri(), user.getAvatarUrl());
        embed.setTitle(Configs.getTranslations().getTitlePrefix() + weapon.getDisplayName());
        embed.addField(price, "**" + weapon.getShopData().getCost() + "**", false);
        embed.addField(type, weapon.getShopData().getCategoryText() + "", false);
        embed.addField(magazine, "**" + weapon.getWeaponStats().getMagazineSize() + "** " + bullets, true);
        embed.addField(fireRate, "**" + weapon.getWeaponStats().getFireRate() + "** " + rps, true);
        embed.addField(equipTime, "**" + weapon.getWeaponStats().getEquipTimeSeconds() + "** " + seconds, true);
        embed.addField(reloadTime, "**" + weapon.getWeaponStats().getReloadTimeSeconds() + "** " + seconds, true);
        for(DamageRanges damageRanges : weapon.getWeaponStats().getDamageRanges()) {
            embed.addField(damage + " (" + damageRanges.getRangeStartMeters() + "-" + damageRanges.getRangeEndMeters() + "m)",
                    head + " - **" + damageRanges.getHeadDamage() + "**\n" +
                            body + " - **" + damageRanges.getBodyDamage() + "**\n" +
                            legs + " - **" + damageRanges.getLegDamage() + "**", false);
        }
        embed.setImage(weapon.getDisplayIcon());
        return embed.build();
    }
}

