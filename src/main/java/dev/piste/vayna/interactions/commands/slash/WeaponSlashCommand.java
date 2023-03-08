package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.OfficerAPI;
import dev.piste.vayna.apis.entities.officer.Weapon;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.translations.LanguageManager;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class WeaponSlashCommand implements ISlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event, Language language) throws HttpErrorException, IOException, InterruptedException {
        event.deferReply().setEphemeral(true).queue();

        // Searching the weapon by the provided UUID
        Weapon weapon = new OfficerAPI().getWeapon(event.getOption("name").getAsString(), language.getLanguageCode());

        // Creating the reply embed
        Embed embed = new Embed()
                .setAuthor(weapon.getDisplayName(), weapon.getKillStreamIcon())
                .setImage(weapon.getDisplayIcon())
                .addField(language.getTranslation("command-weapon-embed-field-1-name"), String.valueOf(weapon.getShopData().getPrice()), false)
                .addField(language.getTranslation("command-weapon-embed-field-2-name"), weapon.getShopData().getCategoryText(), false)
                .addField(language.getTranslation("command-weapon-embed-field-3-name"), weapon.getStats().getMagazineSize() + " " + language.getTranslation("command-weapon-embed-field-3-text"), true)
                .addField(language.getTranslation("command-weapon-embed-field-4-name"), weapon.getStats().getFireRate() + " " + language.getTranslation("command-weapon-embed-field-4-text"), true)
                .addField(language.getTranslation("command-weapon-embed-field-5-name"), weapon.getStats().getEquipTimeSeconds() + " " + language.getTranslation("command-weapon-embed-field-5-text"), true)
                .addField(language.getTranslation("command-weapon-embed-field-6-name"), weapon.getStats().getReloadTimeSeconds() + " " + language.getTranslation("command-weapon-embed-field-6-text"), true)
                .removeFooter();
        for(Weapon.Stats.DamageRange damageRange : weapon.getStats().getDamageRanges()) {
            embed.addField(language.getTranslation("command-weapon-embed-field-7-name") + " (" + damageRange.getRangeStartMeters() + "-" + damageRange.getRangeEndMeters() + "m)",
                    language.getTranslation("command-weapon-embed-field-7-text-1") + " - " + damageRange.getHeadDamageCount() + "\n" +
                            language.getTranslation("command-weapon-embed-field-7-text-2") + " - " + damageRange.getBodyDamageCount() + "\n" +
                            language.getTranslation("command-weapon-embed-field-7-text-3") + " - " + damageRange.getLegDamageCount(), false);
        }

        // Reply
        event.getHook().editOriginalEmbeds(embed.build()).queue();
    }

    @Override
    public CommandData getCommandData() throws HttpErrorException, IOException, InterruptedException {
        OptionData optionData = new OptionData(OptionType.STRING, "name", "Weapon name", true);
        for(Weapon weapon : new OfficerAPI().getWeapons("en-US")) {
            if(weapon.getDisplayName().equalsIgnoreCase("Melee")) continue;
            optionData.addChoice(weapon.getDisplayName(), weapon.getId());
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
