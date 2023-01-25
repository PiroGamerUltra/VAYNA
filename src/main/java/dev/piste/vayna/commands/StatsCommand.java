package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.api.henrik.HenrikAccount;
import dev.piste.vayna.api.riotgames.RiotAccount;
import dev.piste.vayna.embeds.ErrorEmbed;
import dev.piste.vayna.exceptions.HenrikAccountException;
import dev.piste.vayna.exceptions.InvalidRiotIdException;
import dev.piste.vayna.embeds.StatsEmbed;
import dev.piste.vayna.mongodb.LinkedAccount;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class StatsCommand {

    public static void performCommand(SlashCommandInteractionEvent event) {
        RiotAccount riotAccount = null;
        switch (event.getSubcommandName()) {
            case "me" -> {
                long discordUserId = event.getUser().getIdLong();
                LinkedAccount linkedAccount = new LinkedAccount(discordUserId);

                if (!linkedAccount.isExisting()) {
                    event.getHook().editOriginalEmbeds(ErrorEmbed.getSelfRiotAccountNotConnected(event.getUser())).queue();
                    return;
                }
                riotAccount = new RiotAccount(linkedAccount.getRiotPuuid());
            }
            case "user" -> {
                long discordUserId = event.getOption("user").getAsLong();
                LinkedAccount linkedAccount = new LinkedAccount(discordUserId);

                if (!linkedAccount.isExisting()) {
                    if (discordUserId == event.getUser().getIdLong()) {
                        event.getHook().editOriginalEmbeds(ErrorEmbed.getSelfRiotAccountNotConnected(event.getUser())).queue();
                    } else {
                        event.getHook().editOriginalEmbeds(ErrorEmbed.getRiotAccountNotConnected(event.getUser(), Bot.getJDA().getUserById(discordUserId))).queue();
                    }
                    return;
                }
                riotAccount = new RiotAccount(linkedAccount.getRiotPuuid());
            }
            case "riot-id" -> {
                String gameName = event.getOption("name").getAsString();
                String tagLine = event.getOption("tag").getAsString();
                try {
                    riotAccount = new RiotAccount(gameName, tagLine);
                } catch (InvalidRiotIdException e) {
                    event.getHook().editOriginalEmbeds(ErrorEmbed.getRiotIdNotFound(event.getUser(), gameName + "#" + tagLine)).queue();
                    return;
                }
            }
        }

        String regionEmoji = switch (riotAccount.getActiveShard()) {
            case "eu" -> "\uD83C\uDDEA\uD83C\uDDFA";
            case "na" -> "\uD83C\uDDFA\uD83C\uDDF8";
            case "br", "latam" -> "\uD83C\uDDE7\uD83C\uDDF7";
            case "kr" -> "\uD83C\uDDF0\uD83C\uDDF7";
            case "ap" -> "\uD83C\uDDE6\uD83C\uDDFA";
            default -> "none";
        };

        HenrikAccount henrikAccount;
        try {
             henrikAccount = riotAccount.getHenrikAccount();
        } catch (HenrikAccountException e) {
            event.getHook().editOriginalEmbeds(ErrorEmbed.getHenrikApiError(event.getUser())).queue();
            return;
        }

        event.getHook().editOriginalEmbeds(StatsEmbed.getStats(riotAccount.getRiotId(), henrikAccount.getPlayerCardSmall(), henrikAccount.getLevel(), riotAccount.getRegionName(), regionEmoji)).queue();
    }

}
