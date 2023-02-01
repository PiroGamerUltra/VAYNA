package dev.piste.vayna.config.translations;

import dev.piste.vayna.config.Configs;
import dev.piste.vayna.mongodb.GuildSetting;
import net.dv8tion.jda.api.entities.Guild;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Language {

    private String languageCode;
    private Commands commands;
    private Buttons buttons;
    private Modals modals;

    public String getLanguageCode() {
        return languageCode;
    }

    public Commands getCommands() {
        return commands;
    }

    public Buttons getButtons() {
        return buttons;
    }

    public Modals getModals() {
        return modals;
    }

    public static Language getLanguage(Guild guild) {
        if(guild != null) {
            GuildSetting guildSetting = new GuildSetting(guild.getIdLong());
            return Configs.getTranslations().getLanguage(guildSetting.getLanguage());
        } else {
            return Configs.getTranslations().getLanguage("en");
        }
    }
}
