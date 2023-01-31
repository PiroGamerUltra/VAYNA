package dev.piste.vayna.config.translations.commands.stats.errors;

import dev.piste.vayna.commands.ConnectionCommand;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.config.translations.Language;
import dev.piste.vayna.manager.CommandManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Error {

    private String title;
    private String description;

    public MessageEmbed getMessageEmbed(User user, String riotId) {
        Embed embed = new Embed().setAuthor(user.getName(), Configs.getSettings().getWebsiteUri(), user.getAvatarUrl())
                .setColor(255, 0, 0)
                .setTitle(Configs.getTranslations().getTitlePrefix() + title)
                .setDescription(description
                        .replaceAll("%emoji:riotgames%", Emoji.getRiotGames().getFormatted())
                        .replaceAll("%riotid%", riotId));
        return embed.build();
    }

    public MessageEmbed getMessageEmbed(User user) {
        Embed embed = new Embed().setAuthor(user.getName(), Configs.getSettings().getWebsiteUri(), user.getAvatarUrl())
                .setColor(255, 0, 0)
                .setTitle(Configs.getTranslations().getTitlePrefix() + title)
                .setDescription(description.replaceAll("%command:connection%", CommandManager.getAsJdaCommand(new ConnectionCommand()).getAsMention()));
        return embed.build();
    }

    public MessageEmbed getMessageEmbed(User user, User target) {
        Embed embed = new Embed().setAuthor(user.getName(), Configs.getSettings().getWebsiteUri(), user.getAvatarUrl())
                .setColor(255, 0, 0)
                .setTitle(Configs.getTranslations().getTitlePrefix() + title)
                .setDescription(description.replaceAll("%user:target%", target.getAsMention()));
        return embed.build();
    }


}
