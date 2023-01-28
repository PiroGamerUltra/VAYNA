package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.Command;
import dev.piste.vayna.api.henrik.HenrikAccount;
import dev.piste.vayna.api.riotgames.ActiveShard;
import dev.piste.vayna.api.riotgames.RiotAccount;
import dev.piste.vayna.embeds.ErrorEmbed;
import dev.piste.vayna.exceptions.HenrikAccountException;
import dev.piste.vayna.exceptions.RiotAccountException;
import dev.piste.vayna.embeds.StatsEmbed;
import dev.piste.vayna.mongodb.LinkedAccount;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public class StatsCommand implements Command {

    @Override
    public void perform(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        LinkedAccount linkedAccount = null;
        RiotAccount riotAccount = null;
        switch (event.getSubcommandName()) {
            // /stats me
            case "me" -> {
                linkedAccount = new LinkedAccount(event.getUser().getIdLong());

                if (!linkedAccount.isExisting()) {
                    event.getHook().editOriginalEmbeds(ErrorEmbed.getSelfRiotAccountNotConnected(event.getUser())).queue();
                    return;
                }
                riotAccount = RiotAccount.getByPuuid(linkedAccount.getRiotPuuid());
            }
            // /stats user <@user>
            case "user" -> {
                long discordUserId = event.getOption("user").getAsLong();
                linkedAccount = new LinkedAccount(discordUserId);

                if (!linkedAccount.isExisting()) {
                    if (discordUserId == event.getUser().getIdLong()) {
                        event.getHook().editOriginalEmbeds(ErrorEmbed.getSelfRiotAccountNotConnected(event.getUser())).queue();
                    } else {
                        event.getHook().editOriginalEmbeds(ErrorEmbed.getRiotAccountNotConnected(event.getUser(), Bot.getJDA().getUserById(discordUserId))).queue();
                    }
                    return;
                }
                if(!linkedAccount.isVisibleToPublic() && (discordUserId != event.getUser().getIdLong())) {
                    event.getHook().editOriginalEmbeds(ErrorEmbed.getLinkedAccountPrivate(event.getUser())).queue();
                    return;
                }
                riotAccount = RiotAccount.getByPuuid(linkedAccount.getRiotPuuid());

            }
            // /stats riot-id <name> <tag>
            case "riot-id" -> {
                String gameName = event.getOption("name").getAsString();
                String tagLine = event.getOption("tag").getAsString();
                try {
                    riotAccount = RiotAccount.getByRiotId(gameName, tagLine);

                    linkedAccount = new LinkedAccount(riotAccount.getPuuid());
                    if (linkedAccount.isExisting()) {
                        if(!linkedAccount.isVisibleToPublic() && (linkedAccount.getDiscordUserId() != event.getUser().getIdLong())) {
                            event.getHook().editOriginalEmbeds(ErrorEmbed.getLinkedAccountPrivate(event.getUser())).queue();
                            return;
                        }
                    }
                } catch (RiotAccountException e) {
                    event.getHook().editOriginalEmbeds(ErrorEmbed.getRiotIdNotFound(event.getUser(), gameName + "#" + tagLine)).queue();
                    return;
                }
            }
        }

        String regionEmoji;
        String regionName;
        try {
            ActiveShard activeShard = riotAccount.getActiveShard();
            regionEmoji = switch (activeShard.getActiveShard()) {
                case "eu" -> "\uD83C\uDDEA\uD83C\uDDFA";
                case "na" -> "\uD83C\uDDFA\uD83C\uDDF8";
                case "br", "latam" -> "\uD83C\uDDE7\uD83C\uDDF7";
                case "kr" -> "\uD83C\uDDF0\uD83C\uDDF7";
                case "ap" -> "\uD83C\uDDE6\uD83C\uDDFA";
                default -> "none";
            };
            regionName = riotAccount.getActiveShard().getPlatformData().getName();
        } catch (RiotAccountException e) {
            regionEmoji = "❓";
            regionName = "None";
        }

        HenrikAccount henrikAccount;
        try {
            henrikAccount = riotAccount.getHenrikAccount();
        } catch (HenrikAccountException e) {
            event.getHook().editOriginalEmbeds(ErrorEmbed.getHenrikApiError(event.getUser())).queue();
            return;
        }

        String mention = null;
        if(linkedAccount.getDiscordUserId() != 0) {
            mention = Bot.getJDA().getUserById(linkedAccount.getDiscordUserId()).getAsMention();
        }

        event.getHook().editOriginalEmbeds(StatsEmbed.getStats(riotAccount.getRiotId(),
                henrikAccount.getCard().getSmall(),
                henrikAccount.getAccountLevel(),
                regionName,
                regionEmoji,
                mention)).queue();
    }

    @Override
    public void register() {
        SubcommandData userSub = new SubcommandData("user", "Get general information about a VALORANT profile from a Discord user")
                .addOption(OptionType.USER, "user", "The discord user to get the stats from", true);
        SubcommandData riotIdSub = new SubcommandData("riot-id", "Get general information about a VALORANT profile by providing a Riot-ID")
                .addOption(OptionType.STRING, "name", "The name of the Riot-ID (<name>#<tag>)", true)
                .addOption(OptionType.STRING, "tag", "The tag of the Riot-ID (<name>#<tag>)", true);
        SubcommandData meSub = new SubcommandData("me", "Get general information about your VALORANT profile");
        Bot.getJDA().upsertCommand(getName(), getDescription()).addSubcommands(userSub, riotIdSub, meSub).queue();
    }

    @Override
    public String getName() {
        return "stats";
    }

    @Override
    public String getDescription() {
        return "Get general information about a VALORANT profile";
    }
}
