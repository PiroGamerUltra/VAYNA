package dev.piste.vayna.embeds;

import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class ExceptionEmbed {

    public static MessageEmbed getHttpRequestException(String message) {
        Embed embed = new Embed();
        embed.setColor(255, 0, 0);
        embed.setTitle("» Exception (HttpRequest)");
        embed.setDescription(message);
        return embed.build();
    }

}
