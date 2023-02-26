package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.officer.OfficerAPI;
import dev.piste.vayna.interactions.managers.SlashCommand;
import dev.piste.vayna.apis.officer.gson.Weapon;
import dev.piste.vayna.apis.officer.gson.weapon.DamageRanges;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class WeaponSlashCommand implements SlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event) throws HttpErrorException {
        event.deferReply().setEphemeral(true).queue();
        Language language = LanguageManager.getLanguage(event.getGuild());

        // Searching the weapon by the provided UUID
        Weapon weapon = new OfficerAPI().getWeapon(event.getOption("name").getAsString(), language.getLanguageCode());

        // Creating the reply embed
        Embed embed = new Embed()
                .setAuthor(weapon.getDisplayName(), weapon.getKillStreamIcon())
                .setImage(weapon.getDisplayIcon())
                .addField(language.getTranslation("command-weapon-embed-field-1-name"), String.valueOf(weapon.getShopData().getCost()), false)
                .addField(language.getTranslation("command-weapon-embed-field-2-name"), weapon.getShopData().getCategoryText(), false)
                .addField(language.getTranslation("command-weapon-embed-field-3-name"), weapon.getWeaponStats().getMagazineSize() + " " + language.getTranslation("command-weapon-embed-field-3-text"), true)
                .addField(language.getTranslation("command-weapon-embed-field-4-name"), weapon.getWeaponStats().getFireRate() + " " + language.getTranslation("command-weapon-embed-field-4-text"), true)
                .addField(language.getTranslation("command-weapon-embed-field-5-name"), weapon.getWeaponStats().getEquipTimeSeconds() + " " + language.getTranslation("command-weapon-embed-field-5-text"), true)
                .addField(language.getTranslation("command-weapon-embed-field-6-name"), weapon.getWeaponStats().getReloadTimeSeconds() + " " + language.getTranslation("command-weapon-embed-field-6-text"), true)
                .removeFooter();
        for(DamageRanges damageRanges : weapon.getWeaponStats().getDamageRanges()) {
            embed.addField(language.getTranslation("command-weapon-embed-field-7-name") + " (" + damageRanges.getRangeStartMeters() + "-" + damageRanges.getRangeEndMeters() + "m)",
                    language.getTranslation("command-weapon-embed-field-7-text-1") + " - " + damageRanges.getHeadDamage() + "\n" +
                            language.getTranslation("command-weapon-embed-field-7-text-2") + " - " + damageRanges.getBodyDamage() + "\n" +
                            language.getTranslation("command-weapon-embed-field-7-text-3") + " - " + damageRanges.getLegDamage(), false);
        }

        // Reply
        event.getHook().editOriginalEmbeds(embed.build()).queue();
    }

    @Override
    public CommandData getCommandData() throws HttpErrorException {
        OptionData optionData = new OptionData(OptionType.STRING, "name", "Weapon name", true);
        for(Weapon weapon : new OfficerAPI().getWeapons("en-US")) {
            if(weapon.getDisplayName().equalsIgnoreCase("Melee")) continue;
            optionData.addChoice(weapon.getDisplayName(), weapon.getUuid());
        }
        return Commands.slash(getName(), getDescription()).addOptions(optionData);
    }

    @Override
    public String getName() {
        return "weapon";
    }

    @Override
    public String getDescription() {
        return LanguageManager.getLanguage().getTranslation("command-weapon-description");
    }
}
