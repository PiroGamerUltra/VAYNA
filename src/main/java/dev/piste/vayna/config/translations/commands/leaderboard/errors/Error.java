package dev.piste.vayna.config.translations.commands.leaderboard.errors;

import dev.piste.vayna.config.Configs;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Error {

    private String title;
    private String description;

    public MessageEmbed getMessageEmbed(User user) {
        Embed embed = new Embed().setAuthor(user.getName(), Configs.getSettings().getWebsiteUri(), user.getAvatarUrl())
                .setColor(255, 0, 0)
                .setTitle(Configs.getTranslations().getTitlePrefix() + title)
                .setDescription(description);
        return embed.build();
    }

}
