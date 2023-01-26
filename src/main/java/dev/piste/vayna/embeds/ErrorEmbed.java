package dev.piste.vayna.embeds;

import dev.piste.vayna.config.Configs;
import dev.piste.vayna.manager.CommandManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

public class ErrorEmbed {

    private static Embed embed = new Embed();

    private static void setupEmbed(User user) {
        embed.setColor(255, 0, 0);
        embed.setAuthor(user.getName(), Configs.getSettings().getWebsiteUri(), user.getAvatarUrl());
    }

    public static MessageEmbed getRiotIdNotFound(User user, String riotId) {
        setupEmbed(user);
        embed.setTitle("» Error");
        embed.setDescription("The Riot-ID " + Emoji.getRiotGames().getFormatted() + " `" + riotId + "` couldn't be found. Please check your input values and try again.");
        return embed.build();
    }

    public static MessageEmbed getHenrikApiError(User user) {
        setupEmbed(user);
        embed.setTitle("» Error");
        embed.setDescription("There has been an error while retrieving the data for the provided Riot-ID.");
        return embed.build();
    }

    public static MessageEmbed getRiotAccountNotConnected(User user, User target) {
        setupEmbed(user);
        embed.setTitle("» Error");
        embed.setDescription(target.getAsMention() + " hasn't connected his **Riot-Games** account yet.");
        return embed.build();
    }

    public static MessageEmbed getSelfRiotAccountNotConnected(User user) {
        setupEmbed(user);
        embed.setTitle("» Error");
        embed.setDescription("You haven't connected your **Riot-Games** account yet. If you want to, you can do it with " + CommandManager.findCommand("connection").getAsMention() + ".");
        return embed.build();
    }

    public static MessageEmbed getLinkedAccountPrivate(User user) {
        setupEmbed(user);
        embed.setTitle("» Error");
        embed.setDescription("This user has set his account to private mode.");
        return embed.build();
    }

}
