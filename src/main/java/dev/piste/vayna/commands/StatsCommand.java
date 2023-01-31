package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.henrik.HenrikAPI;
import dev.piste.vayna.apis.riotgames.InvalidRegionException;
import dev.piste.vayna.apis.riotgames.RiotAPI;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.config.translations.Language;
import dev.piste.vayna.manager.Command;
import dev.piste.vayna.apis.henrik.gson.HenrikAccount;
import dev.piste.vayna.apis.riotgames.gson.ActiveShard;
import dev.piste.vayna.apis.riotgames.gson.RiotAccount;
import dev.piste.vayna.apis.riotgames.InvalidRiotIdException;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class StatsCommand implements Command {

    @Override
    public void perform(SlashCommandInteractionEvent event) throws StatusCodeException {
        event.deferReply().queue();

        Language language = Language.getLanguage(event.getGuild());

        LinkedAccount linkedAccount = null;
        RiotAccount riotAccount = null;
        long discordUserId = 0;
        if(event.getSubcommandName() == null) return;
        switch (event.getSubcommandName()) {
            // /stats me
            case "me" -> {
                discordUserId = event.getUser().getIdLong();
                linkedAccount = new LinkedAccount(event.getUser().getIdLong());
                riotAccount = RiotAPI.getAccountByPuuid(linkedAccount.getRiotPuuid());
            }
            // /stats user <@user>
            case "user" -> {
                discordUserId = event.getOption("user").getAsLong();
                linkedAccount = new LinkedAccount(discordUserId);
                riotAccount = RiotAPI.getAccountByPuuid(linkedAccount.getRiotPuuid());
            }
            // /stats riot-id <name> <tag>
            case "riot-id" -> {
                String gameName = event.getOption("name").getAsString();
                String tagLine = event.getOption("tag").getAsString();
                try {
                    riotAccount = RiotAPI.getAccountByRiotId(gameName, tagLine);

                    linkedAccount = new LinkedAccount(riotAccount.getPuuid());
                } catch (InvalidRiotIdException e) {
                    event.getHook().editOriginalEmbeds(language.getCommands().getStats().getErrors().getRiotId().getMessageEmbed(event.getUser(), gameName + "#" + tagLine)).setActionRow(
                            language.getErrors().getSupportButton()
                    ).queue();
                    return;
                }
            }
        }

        if (linkedAccount.isExisting()) {
            if(!linkedAccount.isVisibleToPublic() && (linkedAccount.getDiscordUserId() != event.getUser().getIdLong())) {
                event.getHook().editOriginalEmbeds(language.getCommands().getStats().getErrors().getPrivateAccount().getMessageEmbed(event.getUser())).setActionRow(
                        language.getErrors().getSupportButton()
                ).queue();
                return;
            }
        } else {
            if(discordUserId != 0) {
                if (discordUserId == event.getUser().getIdLong()) {
                    event.getHook().editOriginalEmbeds(language.getCommands().getStats().getErrors().getNoConnectionSelf().getMessageEmbed(event.getUser())).setActionRow(
                            language.getErrors().getSupportButton()
                    ).queue();
                } else {
                    event.getHook().editOriginalEmbeds(language.getCommands().getStats().getErrors().getNoConnection().getMessageEmbed(event.getUser(), event.getJDA().getUserById(discordUserId))).setActionRow(
                            language.getErrors().getSupportButton()
                    ).queue();
                }
                return;
            }
        }

        ActiveShard activeShard;
        try {
            activeShard = RiotAPI.getActiveShard(riotAccount.getPuuid());
        } catch (InvalidRegionException e) {
            event.getHook().editOriginalEmbeds(language.getCommands().getStats().getErrors().getRegion().getMessageEmbed(event.getUser(), riotAccount.getRiotId())).queue();
            return;
        }

        String regionEmoji = switch (activeShard.getActiveShard()) {
            case "eu" -> "\uD83C\uDDEA\uD83C\uDDFA";
            case "na" -> "\uD83C\uDDFA\uD83C\uDDF8";
            case "br", "latam" -> "\uD83C\uDDE7\uD83C\uDDF7";
            case "kr" -> "\uD83C\uDDF0\uD83C\uDDF7";
            case "ap" -> "\uD83C\uDDE6\uD83C\uDDFA";
            default -> "none";
        };
        String regionName = RiotAPI.getPlatformData(activeShard.getActiveShard()).getName();

        HenrikAccount henrikAccount = HenrikAPI.getAccountByRiotId(riotAccount.getGameName(), riotAccount.getTagLine());

        event.getHook().editOriginalEmbeds(language.getCommands().getStats().getMessageEmbed(riotAccount, henrikAccount, linkedAccount, regionEmoji, regionName)).setActionRow(
            language.getCommands().getStats().getRankButton(riotAccount)
        ).queue();
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
