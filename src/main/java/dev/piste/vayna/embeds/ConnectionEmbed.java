package dev.piste.vayna.embeds;

import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class ConnectionEmbed {

    public static MessageEmbed getConnectionPresent(String riotId, String discordMention, boolean visibleToPublic) {
        Embed embed = new Embed();
        embed.setColor(209, 54, 57);
        embed.setTitle("» Connection");
        embed.setDescription("Your **Discord** account is currently connected with your **Riot-Games** account. To disconnect it, click the button below.");
        embed.addField("Connection", discordMention + " " + Emoji.getDiscord().getFormatted() + " \uD83D\uDD17 " + Emoji.getRiotGames().getFormatted() + " `" + riotId + "`", true);
        embed.addField("Visibility", visibleToPublic ? "\uD83D\uDD13 Public" : "\uD83D\uDD12 Private", true);
        return embed.build();
    }

    public static MessageEmbed getNoConnectionPresent(String discordMention) {
        Embed embed = new Embed();
        embed.setColor(209, 54, 57);
        embed.setTitle("» Connection");
        embed.setDescription("Your **Discord** account is currently not connected with a **Riot-Games** account. To connect yours, click the button below.");
        embed.addField("Connection", discordMention + " " + Emoji.getDiscord().getFormatted() + " " + Emoji.getBlank().getFormatted() + " " + Emoji.getRiotGames().getFormatted() + " `Not connected`", false);
        return embed.build();
    }

}
