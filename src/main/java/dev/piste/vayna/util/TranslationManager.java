package dev.piste.vayna.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.mongodb.GuildSetting;
import net.dv8tion.jda.api.entities.Guild;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

/**
 * @author Piste | https://github.com/zPiste
 */
public class TranslationManager {

    private HashMap<String, String> translations;

    public static TranslationManager getTranslation(Guild guild) {
        if(guild == null) {
            return new TranslationManager().loadTranslations("en-US");
        } else {
            GuildSetting guildSetting = new GuildSetting(guild.getIdLong());
            return new TranslationManager().loadTranslations(guildSetting.getLanguage());
        }
    }

    public static TranslationManager getTranslation() {
        return new TranslationManager().loadTranslations("en-US");
    }

    public TranslationManager() {
        translations = new HashMap<>();
    }

    public TranslationManager loadTranslations(String languageCode) {
        Gson gson = new Gson();
        try {
            translations = gson.fromJson(new FileReader("translations/" + languageCode + ".json"), new TypeToken<HashMap<String, String>>() {}.getType());
            return this;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTranslation(String messageKey) {
        if (translations.containsKey(messageKey)) {
            return translations.get(messageKey);
        }
        return "Translation not found";
    }

}
