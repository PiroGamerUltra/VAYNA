package dev.piste.vayna.config.translations.commands.connection;

import dev.piste.vayna.config.Configs;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Present {

    private String title;
    private String description;
    private String connection;
    private String visibility;
    private String publicState;
    private String privateState;

    public MessageEmbed getMessageEmbed(User user, String riotId, boolean visibleToPublic) {
        Embed embed = new Embed();
        embed.setAuthor(user.getName(), Configs.getSettings().getWebsiteUri(), user.getAvatarUrl());
        embed.setColor(209, 54, 57);
        embed.setTitle(Configs.getTranslations().getTitlePrefix() + title);
        embed.setDescription(description);
        embed.addField(connection, user.getAsMention() + " " + Emoji.getDiscord().getFormatted() + " \uD83D\uDD17 "
                + Emoji.getRiotGames().getFormatted() + " `" + riotId + "`", true);
        embed.addField(visibility, visibleToPublic ? "\uD83D\uDD13 " + publicState : "\uD83D\uDD12 " + privateState, true);
        return embed.build();
    }

}
