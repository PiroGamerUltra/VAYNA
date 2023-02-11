package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.henrik.HenrikAPI;
import dev.piste.vayna.apis.riotgames.InvalidRegionException;
import dev.piste.vayna.apis.riotgames.RiotAPI;
import dev.piste.vayna.manager.Command;
import dev.piste.vayna.apis.henrik.gson.HenrikAccount;
import dev.piste.vayna.apis.riotgames.gson.ActiveShard;
import dev.piste.vayna.apis.riotgames.gson.RiotAccount;
import dev.piste.vayna.apis.riotgames.InvalidRiotIdException;
import dev.piste.vayna.manager.CommandManager;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import dev.piste.vayna.util.buttons.Buttons;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

/**
 * @author Piste | https://github.com/zPiste
 */
public class StatsCommand implements Command {

    @Override
    public void perform(SlashCommandInteractionEvent event) throws StatusCodeException {
        event.deferReply().queue();

        Language language = LanguageManager.getLanguage(event.getGuild());

        LinkedAccount linkedAccount = null;
        RiotAccount riotAccount = null;
        long discordUserId = 0;
        if(event.getSubcommandName() == null) return;
        switch (event.getSubcommandName()) {
            // /stats me
            case "me" -> {
                discordUserId = event.getUser().getIdLong();
                linkedAccount = new LinkedAccount(event.getUser().getIdLong());
                if(linkedAccount.isExisting()) {
                    riotAccount = RiotAPI.getAccountByPuuid(linkedAccount.getRiotPuuid());
                }
            }
            // /stats user <@user>
            case "user" -> {
                discordUserId = event.getOption("user").getAsUser().getIdLong();
                linkedAccount = new LinkedAccount(discordUserId);
                if(linkedAccount.isExisting()) {
                    riotAccount = RiotAPI.getAccountByPuuid(linkedAccount.getRiotPuuid());
                }
            }
            // /stats riot-id <name> <tag>
            case "riot-id" -> {
                String gameName = event.getOption("name").getAsString();
                String tagLine = event.getOption("tag").getAsString();
                try {
                    riotAccount = RiotAPI.getAccountByRiotId(gameName, tagLine);

                    linkedAccount = new LinkedAccount(riotAccount.getPuuid());
                } catch (InvalidRiotIdException e) {
                    Embed embed = new Embed().setAuthor(event.getUser().getName(), event.getUser().getAvatarUrl())
                            .setColor(255, 0, 0)
                            .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-stats-error-riotid-embed-title"))
                            .setDescription(language.getTranslation("command-stats-error-riotid-embed-description")
                                    .replaceAll("%emoji:riotgames%", Emoji.getRiotGames().getFormatted())
                                    .replaceAll("%riotid%", gameName + "#" + tagLine));
                    event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                            Buttons.getSupportButton(event.getGuild())
                    ).queue();
                    return;
                }
            }
        }

        if (linkedAccount.isExisting()) {
            if(!linkedAccount.isVisibleToPublic() && (linkedAccount.getDiscordUserId() != event.getUser().getIdLong())) {
                Embed embed = new Embed().setAuthor(event.getUser().getName(), event.getUser().getAvatarUrl())
                        .setColor(255, 0, 0)
                        .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-stats-error-private-embed-title"))
                        .setDescription(language.getTranslation("command-stats-error-private-embed-description"));
                event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                        Buttons.getSupportButton(event.getGuild())
                ).queue();
                return;
            }
        } else {
            if(discordUserId != 0) {
                if (discordUserId == event.getUser().getIdLong()) {
                    Embed embed = new Embed().setAuthor(event.getUser().getName(), event.getUser().getAvatarUrl())
                            .setColor(255, 0, 0)
                            .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-stats-error-noconnectionself-embed-title"))
                            .setDescription(language.getTranslation("command-stats-error-noconnectionself-embed-description")
                                    .replaceAll("%command:connection%", CommandManager.getAsJdaCommand(new ConnectionCommand()).getAsMention()));
                    event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                            Buttons.getSupportButton(event.getGuild())
                    ).queue();
                } else {
                        Embed embed = new Embed().setAuthor(event.getUser().getName(), event.getUser().getAvatarUrl())
                                .setColor(255, 0, 0)
                                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-stats-error-noconnection-embed-title"))
                                .setDescription(language.getTranslation("command-stats-error-noconnection-embed-description")
                                        .replaceAll("%user:target%", event.getJDA().getUserById(discordUserId).getAsMention()));
                    event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                            Buttons.getSupportButton(event.getGuild())
                    ).queue();
                }
                return;
            }
        }

        ActiveShard activeShard;
        try {
            activeShard = RiotAPI.getActiveShard(riotAccount.getPuuid());
        } catch (InvalidRegionException e) {
            Embed embed = new Embed().setAuthor(event.getUser().getName(), event.getUser().getAvatarUrl())
                    .setColor(255, 0, 0)
                    .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-stats-error-region-embed-title"))
                    .setDescription(language.getTranslation("command-stats-error-region-embed-description")
                            .replaceAll("%emoji:riotgames%", Emoji.getRiotGames().getFormatted())
                            .replaceAll("%riotid%", riotAccount.getRiotId()));
            event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                    Buttons.getSupportButton(event.getGuild())
            ).queue();
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

            Embed embed = new Embed();
            embed.setAuthor(riotAccount.getRiotId(), henrikAccount.getCard() != null ? henrikAccount.getCard().getSmall() : null);
            embed.setColor(209, 54, 57);
            embed.setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-stats-embed-title"));
            embed.setDescription(language.getTranslation("command-stats-embed-description"));
            embed.addField(language.getTranslation("command-stats-embed-field-1-name"), Emoji.getLevel().getFormatted() + " " + henrikAccount.getAccountLevel(), true);
            embed.addField(language.getTranslation("command-stats-embed-field-2-name"), regionEmoji + " " + regionName, true);
            if(linkedAccount.isExisting()) {
                embed.addField(language.getTranslation("command-stats-embed-field-3-name"),
                        Emoji.getDiscord().getFormatted() + " " + Bot.getJDA().getUserById(linkedAccount.getDiscordUserId()).getAsMention() + " (`" + Bot.getJDA().getUserById(linkedAccount.getDiscordUserId()).getAsTag() + "`)", true);
            }
        event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
            Buttons.getRankButton(event.getGuild(), riotAccount)
        ).queue();
    }

    @Override
    public CommandData getCommandData() {
        SubcommandData userSub = new SubcommandData("user", LanguageManager.getLanguage().getTranslation("command-stats-user-description"))
                .addOption(OptionType.USER, "user", "Discord user", true);
        SubcommandData riotIdSub = new SubcommandData("riot-id", LanguageManager.getLanguage().getTranslation("command-stats-riotid-description"))
                .addOption(OptionType.STRING, "name", "The name of the Riot-ID (<name>#<tag>)", true)
                .addOption(OptionType.STRING, "tag", "The tag of the Riot-ID (<name>#<tag>)", true);
        SubcommandData meSub = new SubcommandData("me", LanguageManager.getLanguage().getTranslation("command-stats-me-description"));
        return Commands.slash(getName(), getDescription()).addSubcommands(userSub, riotIdSub, meSub);
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
