package dev.piste.vayna.commands;

import dev.piste.vayna.api.riotgames.RiotAccount;
import dev.piste.vayna.api.riotgames.UnknownRiotIdException;
import dev.piste.vayna.config.SettingsConfig;
import dev.piste.vayna.manager.CommandManager;
import dev.piste.vayna.mongodb.Mongo;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class StatsCommand {

    public static void performCommand(SlashCommandInteractionEvent event) {
        RiotAccount account = null;
        switch (event.getSubcommandName()) {
            case "user" -> {
                long discordId = (event.getOption("user") == null) ? event.getUser().getIdLong() : event.getOption("user").getAsLong();
                Document foundAccount = Mongo.getLinkedAccountsCollection().find(eq("discordId", discordId)).first();
                if (foundAccount == null) {
                    Embed embed = new Embed();
                    embed.setColor(255, 0, 0);
                    embed.setAuthor(event.getUser().getName(), SettingsConfig.getWebsiteUri(), event.getUser().getAvatarUrl());
                    embed.setTitle("» Discord account not connected");
                    if (discordId == event.getUser().getIdLong()) {
                        embed.setDescription("You haven't connected your Riot-Games account yet. You can do it with " + CommandManager.findSubcommand(CommandManager.findCommand("connection"), "connect").getAsMention());
                    } else {
                        embed.setDescription("This Discord user hasn't connected his Riot-Games account yet.");
                    }
                    event.getHook().editOriginalEmbeds(embed.build()).queue();
                    return;
                }
                account = new RiotAccount(foundAccount.getString("puuid"));
            }
            case "riotid" -> {
                String gameName = event.getOption("name").getAsString();
                String tagLine = event.getOption("tag").getAsString();
                try {
                    account = new RiotAccount(gameName, tagLine);
                } catch (UnknownRiotIdException e) {
                    Embed embed = new Embed();
                    embed.setColor(255, 0, 0);
                    embed.setAuthor(event.getUser().getName(), SettingsConfig.getWebsiteUri(), event.getUser().getAvatarUrl());
                    embed.setTitle("» Riot-ID not found");
                    embed.setDescription("The provided Riot-ID doesn't exist.");
                    event.getHook().editOriginalEmbeds(embed.build()).queue();
                    return;
                }
            }
        }

        Embed embed = new Embed();
        embed.setAuthor(account.getRiotId(), SettingsConfig.getWebsiteUri(), account.getHenrikAccount().getPlayerCardSmall());
        embed.addField("Level", Emoji.fromCustom("level", 862382934355214385L, false).getAsMention() + " " + account.getHenrikAccount().getLevel(), true);
        String regionEmoji = switch (account.getActiveShard()) {
            case "eu" -> "\uD83C\uDDEA\uD83C\uDDFA";
            case "na" -> "\uD83C\uDDFA\uD83C\uDDF8";
            case "br", "latam" -> "\uD83C\uDDE7\uD83C\uDDF7";
            case "kr" -> "\uD83C\uDDF0\uD83C\uDDF7";
            case "ap" -> "\uD83C\uDDE6\uD83C\uDDFA";
            default -> "none";
        };
        embed.addField("Region", regionEmoji + " " + account.getRegionName(), true);
        event.getHook().editOriginalEmbeds(embed.build()).queue();
    }

}
