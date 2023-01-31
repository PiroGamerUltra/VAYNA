package dev.piste.vayna.config.translations.commands.connection;

import dev.piste.vayna.config.Configs;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

/**
 * @author Piste | https://github.com/zPiste
 */
public class None {

    private String title;
    private String description;

    public MessageEmbed getMessageEmbed(User user) {
        Embed embed = new Embed();
        embed.setColor(209, 54, 57);
        embed.setAuthor(user.getName(), Configs.getSettings().getWebsiteUri(), user.getAvatarUrl());
        embed.setTitle(Configs.getTranslations().getTitlePrefix() + title);
        embed.setDescription(description);
        return embed.build();
    }

}
