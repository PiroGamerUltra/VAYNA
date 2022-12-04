package dev.piste.vayna.commands;

import dev.piste.vayna.main.Bot;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class HelpCommand {

    public static void performCommand(SlashCommandInteractionEvent event) {

        Embed embedBuilder = new Embed();
        embedBuilder.setColor(157, 57, 191);
        embedBuilder.setTitle("» Help");
        embedBuilder.addField("Version", "The current version of the bot is `v" + Bot.getVersion() + "`", false);
        embedBuilder.addField("Creator", "I've been developed by **Piste#1111**", false);
        embedBuilder.addField("General Commands",  "» `/help` Get informations about the bot and commands.", false);
        embedBuilder.addField("Statistic Commands",  "» `/stats user <@User>` Retrieve the stats of a player by tagging the discord user. (\\⚠ The user must first be verified with **/verify** \\⚠)\n" +
                "» `/stats riotid <Riot-Name> <Riot-Tag>` Retrieve the stats from a player by providing the Riot-ID.\n" +
                "» `/leaderboard` Show the leaderboard of the VALORANT ranks of your discord server. (\\⚠ To be on the leaderboard, you must first be verified with **/verify** \\⚠)", false);
        embedBuilder.addField("Verification Commands",  "» `/verify <Riot-Name> <Riot-Tag>` Verify yourself to be displayed on the leaderboard and more.\n" +
                "» `/unverify` Unverify yourself. This means that you will be removed from our database, but you can verify yourself again at any time.", false);
        embedBuilder.addField("Information Commands",  "» `/map <Map>` Get informations about a certain VALORANT map.\n" +
                "» `/agent <Agent>` Get informations about a certain VALORANT agent.\n" +
                "» `/weapon <Weapon>` Get informations about a certain VALORANT weapon.\n" +
                "» `/gamemode <Gamemode>` Get informations about a certain VALORANT gamemode.\n" +
                "» `/shop` Get informations about the current bundle in the VALORANT shop.", false);
        embedBuilder.addField("Channel Commands", "» `/setchannel leaderboard <Text channel>` Determine the channel in which the leaderboard of the server should be posted every 10 minutes.\n" +
                "» `/setchannel valo-news <Text channel>` Determine the channel in which VALORANT news should be posted.\n" +
                "» `/removechannel leaderboard` Remove the text channel for the leaderboard announcements from the database.\n" +
                "» `/removechannel valo-news` Remove the text channel for the VALORANT news announcements from the database.", false);
        embedBuilder.setAuthor(event.getMember().getUser().getName(), "https://piste.dev/VAYNA/", event.getMember().getUser().getAvatarUrl());

        event.getHook().editOriginalEmbeds(embedBuilder.build()).setActionRow(
                Button.link("https://github.com/zPiste/VAYNA/", "GitHub"),
                Button.link("https://top.gg/de/bot/868403082961236019", "Top.GG")
        ).queue();

    }

}
