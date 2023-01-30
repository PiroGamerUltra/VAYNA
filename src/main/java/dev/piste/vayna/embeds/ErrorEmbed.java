package dev.piste.vayna.embeds;

import dev.piste.vayna.commands.ConnectionCommand;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.manager.CommandManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

public class ErrorEmbed {

    private static final Embed embed = new Embed();

    private static void setupEmbed(User user) {
        embed.setColor(255, 0, 0);
        embed.setAuthor(user.getName(), Configs.getSettings().getWebsiteUri(), user.getAvatarUrl());
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
        embed.setDescription("You haven't connected your **Riot-Games** account yet. If you want to, you can do it with " + CommandManager.getAsJdaCommand(new ConnectionCommand()).getAsMention() + ".");
        return embed.build();
    }

    public static MessageEmbed getLinkedAccountPrivate(User user) {
        setupEmbed(user);
        embed.setTitle("» Error");
        embed.setDescription("This user has set his account to private mode.");
        return embed.build();
    }

    public static MessageEmbed getButtonTooOld(User user) {
        setupEmbed(user);
        embed.setTitle("» Error");
        embed.setDescription("This button is too old. Please execute the command again and try again.");
        return embed.build();
    }

    public static MessageEmbed getNoGuildChannel(User user) {
        setupEmbed(user);
        embed.setTitle("» Error");
        embed.setDescription("This command is only usable in guilds.");
        return embed.build();
    }

}
