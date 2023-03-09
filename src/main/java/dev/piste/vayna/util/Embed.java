package dev.piste.vayna.util;

import dev.piste.vayna.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class Embed {

    private final EmbedBuilder embedBuilder;

    private boolean colorModified = false;
    private boolean footerModified = false;
    private static final int DEFAULT_COLOR_RGB = new Color(157, 57, 191).getRGB();
    private static final String DEFAULT_FOOTER_TEXT = "VAYNA • VALORANT BOT";
    private static final String DEFAULT_FOOTER_ICON_URL = Bot.getJDA().getSelfUser().getAvatarUrl();

    public Embed() {
        this.embedBuilder = new EmbedBuilder();
    }

    public Embed setDescription(String description) {
        embedBuilder.setDescription(description);
        return this;
    }

    public Embed setThumbnail(String url) {
        embedBuilder.setThumbnail(url);
        return this;
    }

    public Embed addField(String name, String text, boolean inline) {
        embedBuilder.addField(name, text, inline);
        return this;
    }

    public Embed setColor(int red, int green, int blue) {
        embedBuilder.setColor(new Color(red, green, blue).getRGB());
        colorModified = true;
        return this;
    }

    public Embed setTitle(String title) {
        embedBuilder.setTitle(title);
        return this;
    }

    public Embed setTitle(String title, String titleUrl) {
        embedBuilder.setTitle(title, titleUrl);
        return this;
    }

    public Embed setAuthor(String name, String iconUrl) {
        embedBuilder.setAuthor(name, null, iconUrl);
        return this;
    }

    public Embed setAuthor(String name) {
        embedBuilder.setAuthor(name);
        return this;
    }

    public Embed setImage(String url) {
        embedBuilder.setImage(url);
        return this;
    }

    public Embed removeFooter() {
        footerModified = true;
        return this;
    }

    public MessageEmbed build() {
        if(!colorModified) embedBuilder.setColor(DEFAULT_COLOR_RGB);
        if(!footerModified) embedBuilder.setFooter(DEFAULT_FOOTER_TEXT, DEFAULT_FOOTER_ICON_URL);
        return embedBuilder.build();
    }

}
