package dev.piste.vayna.config.translations.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.henrik.gson.HenrikAccount;
import dev.piste.vayna.apis.riotgames.gson.RiotAccount;
import dev.piste.vayna.buttons.RankButton;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.config.translations.commands.stats.Errors;
import dev.piste.vayna.manager.ButtonManager;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.UUID;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Stats {

    private String title;
    private String description;
    private String level;
    private String region;
    private String connection;
    private String rankButton;
    private Errors errors;

    public MessageEmbed getMessageEmbed(RiotAccount riotAccount, HenrikAccount henrikAccount, LinkedAccount linkedAccount, String regionEmoji, String regionName) {
        Embed embed = new Embed();
        embed.setAuthor(riotAccount.getRiotId(), Configs.getSettings().getWebsiteUri(), henrikAccount.getCard().getSmall());
        embed.setColor(209, 54, 57);
        embed.setTitle(Configs.getTranslations().getTitlePrefix() + title);
        embed.setDescription(description);
        embed.addField(level, Emoji.getLevel().getFormatted() + " " + henrikAccount.getAccountLevel(), true);
        embed.addField(region, regionEmoji + " " + regionName, true);
        if(linkedAccount.isExisting()) {
            embed.addField(connection, Emoji.getDiscord().getFormatted() + " " + Bot.getJDA().getUserById(linkedAccount.getDiscordUserId()).getAsMention() + " (`" + Bot.getJDA().getUserById(linkedAccount.getDiscordUserId()).getAsTag() + "`)", true);
        }
        return embed.build();
    }

    public Button getRankButton(RiotAccount riotAccount) {
        String uuid = UUID.randomUUID().toString();
        ButtonManager.putInStatsButtonMap(uuid, riotAccount);
        return Button.secondary(new RankButton().getName() + uuid, rankButton).withEmoji(Emoji.getRankByTierName("Unranked"));
    }

    public Errors getErrors() {
        return errors;
    }
}
