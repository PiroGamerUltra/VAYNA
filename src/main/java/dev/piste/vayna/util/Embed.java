package dev.piste.vayna.util;
/*
 * Author: Piste
 * Date: 26.05.2021
 */

import dev.piste.vayna.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;

public class Embed {

    private String description;
    private String thumbnailUrl;
    private ArrayList<MessageEmbed.Field> fieldList = new ArrayList<>();
    private int colorRGB = new Color(157, 57, 191).getRGB();
    private String title;
    private String titleUrl;
    private String authorName;
    private String authorUrl;
    private String authorIconUrl;
    private String imageUrl;

    public Embed() {}

    public Embed setDescription(String description) {
        this.description = description;
        return this;
    }

    public Embed setThumbnail(String url) {
        this.thumbnailUrl = url;
        return this;
    }

    public Embed addField(String name, String text, boolean inline) {
        MessageEmbed.Field field = new MessageEmbed.Field(name, text, inline);
        fieldList.add(field);
        return this;
    }

    public Embed addBlankField(boolean inline) {
        MessageEmbed.Field field = new MessageEmbed.Field("\u200e", "\u200e", inline);
        fieldList.add(field);
        return this;
    }

    public Embed setColor(int red, int green, int blue) {
        this.colorRGB = new Color(red, green, blue).getRGB();
        return this;
    }

    public Embed setTitle(String title) {
        this.title = title;
        return this;
    }

    public Embed setTitle(String title, String titleUrl) {
        this.title = title;
        this.titleUrl = titleUrl;
        return this;
    }



    public Embed setAuthor(String name, String url, String iconUrl) {
        this.authorName = name;
        this.authorUrl = url;
        this.authorIconUrl = iconUrl;
        return this;
    }

    public Embed setAuthor(String name, String url) {
        this.authorName = name;
        this.authorUrl = url;
        return this;
    }

    public Embed setAuthor(String name) {
        this.authorName = name;
        return this;
    }

    public Embed setImage(String url) {
        this.imageUrl = url;
        return this;
    }

    public MessageEmbed build() {
        EmbedBuilder builder = new EmbedBuilder();
        for
        (MessageEmbed.Field field : fieldList) {
            builder.addField(field);
        }
        if (authorName != null) {
            if (authorUrl != null) {
                if (authorIconUrl != null) {
                    builder.setAuthor(authorName, authorUrl, authorIconUrl);
                } else {
                    builder.setAuthor(authorName, authorUrl);
                }
            } else {
                builder.setAuthor(authorName);
            }
        }
        if (thumbnailUrl != null) {
            builder.setThumbnail(thumbnailUrl);
        }
        if (title != null) {
            if (titleUrl != null) {
                builder.setTitle(title, titleUrl);
            } else {
                builder.setTitle(title);
            }
        }

        if (imageUrl != null) builder.setImage(imageUrl);
        builder.setColor(colorRGB);
        if (description != null) {
            builder.setDescription(description);
        }
        builder.setFooter("VAYNA • VALORANT BOT", Bot.getJDA().getSelfUser().getAvatarUrl());
        return builder.build();

    }

}
