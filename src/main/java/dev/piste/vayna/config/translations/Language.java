package dev.piste.vayna.config.translations;

import dev.piste.vayna.config.Configs;
import dev.piste.vayna.mongodb.GuildSetting;
import net.dv8tion.jda.api.entities.Guild;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Language {

    private String prefix;
    private String languageCode;
    private Errors errors;
    private Commands commands;

    public String getPrefix() {
        return prefix;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public Errors getError() {
        return errors;
    }

    public Commands getCommands() {
        return commands;
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
