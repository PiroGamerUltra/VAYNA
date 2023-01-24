package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.api.riotgames.RiotAccount;
import dev.piste.vayna.api.riotgames.UnknownRiotIdException;
import dev.piste.vayna.embeds.StatsEmbed;
import dev.piste.vayna.mongodb.LinkedAccount;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class StatsCommand {

    public static void performCommand(SlashCommandInteractionEvent event) {
        RiotAccount account = null;
        switch (event.getSubcommandName()) {
            case "user" -> {
                long discordUserId = (event.getOption("user") == null) ? event.getUser().getIdLong() : event.getOption("user").getAsLong();
                LinkedAccount linkedAccount = new LinkedAccount(discordUserId);

                if (!linkedAccount.isExisting()) {
                    if (discordUserId == event.getUser().getIdLong()) {
                        event.getHook().editOriginalEmbeds(StatsEmbed.getSelfRiotAccountNotConnected(event.getUser())).queue();
                    } else {
                        event.getHook().editOriginalEmbeds(StatsEmbed.getRiotAccountNotConnected(event.getUser(), Bot.getJDA().getUserById(discordUserId))).queue();
                    }
                    return;
                }
                account = new RiotAccount(linkedAccount.getRiotPuuid());
            }
            case "riotid" -> {
                String gameName = event.getOption("name").getAsString();
                String tagLine = event.getOption("tag").getAsString();
                try {
                    account = new RiotAccount(gameName, tagLine);
                } catch (UnknownRiotIdException e) {
                    event.getHook().editOriginalEmbeds(StatsEmbed.getRiotIdNotFound(event.getUser(), gameName + "#" + tagLine)).queue();
                    return;
                }
            }
        }

        String regionEmoji = switch (account.getActiveShard()) {
            case "eu" -> "\uD83C\uDDEA\uD83C\uDDFA";
            case "na" -> "\uD83C\uDDFA\uD83C\uDDF8";
            case "br", "latam" -> "\uD83C\uDDE7\uD83C\uDDF7";
            case "kr" -> "\uD83C\uDDF0\uD83C\uDDF7";
            case "ap" -> "\uD83C\uDDE6\uD83C\uDDFA";
            default -> "none";
        };
        event.getHook().editOriginalEmbeds(StatsEmbed.getStats(account.getRiotId(), account.getHenrikAccount().getPlayerCardSmall(), account.getHenrikAccount().getLevel(), account.getRegionName(), regionEmoji)).queue();
    }

}
