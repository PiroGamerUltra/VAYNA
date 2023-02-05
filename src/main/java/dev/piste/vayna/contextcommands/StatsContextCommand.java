package dev.piste.vayna.contextcommands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.henrik.HenrikAPI;
import dev.piste.vayna.apis.henrik.gson.HenrikAccount;
import dev.piste.vayna.apis.riotgames.InvalidRegionException;
import dev.piste.vayna.apis.riotgames.RiotAPI;
import dev.piste.vayna.apis.riotgames.gson.ActiveShard;
import dev.piste.vayna.apis.riotgames.gson.RiotAccount;
import dev.piste.vayna.commands.ConnectionCommand;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.manager.CommandManager;
import dev.piste.vayna.manager.UserContextCommand;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import dev.piste.vayna.util.TranslationManager;
import dev.piste.vayna.util.buttons.Buttons;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/**
 * @author Piste | https://github.com/zPiste
 */
public class StatsContextCommand implements UserContextCommand {


    @Override
    public void perform(UserContextInteractionEvent event) throws StatusCodeException {
        event.deferReply().queue();

        TranslationManager translation = TranslationManager.getTranslation(event.getGuild());

        LinkedAccount linkedAccount = new LinkedAccount(event.getTarget().getIdLong());
        if(linkedAccount.isExisting()) {
            if(!linkedAccount.isVisibleToPublic() && (linkedAccount.getDiscordUserId() != event.getUser().getIdLong())) {
                Embed embed = new Embed().setAuthor(event.getUser().getName(), Configs.getSettings().getWebsiteUri(), event.getUser().getAvatarUrl())
                        .setColor(255, 0, 0)
                        .setTitle(translation.getTranslation("embed-title-prefix") + translation.getTranslation("command-stats-error-private-embed-title"))
                        .setDescription(translation.getTranslation("command-stats-error-private-embed-description"));
                event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                        Buttons.getSupportButton(event.getGuild())
                ).queue();
                return;
            }
        } else {
            if (linkedAccount.getDiscordUserId() == event.getUser().getIdLong()) {
                Embed embed = new Embed().setAuthor(event.getUser().getName(), Configs.getSettings().getWebsiteUri(), event.getUser().getAvatarUrl())
                        .setColor(255, 0, 0)
                        .setTitle(translation.getTranslation("embed-title-prefix") + translation.getTranslation("command-stats-error-noconnectionself-embed-title"))
                        .setDescription(translation.getTranslation("command-stats-error-noconnectionself-embed-description")
                                .replaceAll("%command:connection%", CommandManager.getAsJdaCommand(new ConnectionCommand()).getAsMention()));
                event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                        Buttons.getSupportButton(event.getGuild())
                ).queue();
            } else {
                Embed embed = new Embed().setAuthor(event.getUser().getName(), Configs.getSettings().getWebsiteUri(), event.getUser().getAvatarUrl())
                        .setColor(255, 0, 0)
                        .setTitle(translation.getTranslation("embed-title-prefix") + translation.getTranslation("command-stats-error-noconnection-embed-title"))
                        .setDescription(translation.getTranslation("command-stats-error-noconnection-embed-description")
                                .replaceAll("%user:target%", event.getTarget().getAsMention()));
                event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                        Buttons.getSupportButton(event.getGuild())
                ).queue();
            }
            return;
        }

        RiotAccount riotAccount = RiotAPI.getAccountByPuuid(linkedAccount.getRiotPuuid());

        ActiveShard activeShard;
        try {
            activeShard = RiotAPI.getActiveShard(riotAccount.getPuuid());
        } catch (InvalidRegionException e) {
            Embed embed = new Embed().setAuthor(event.getUser().getName(), Configs.getSettings().getWebsiteUri(), event.getUser().getAvatarUrl())
                    .setColor(255, 0, 0)
                    .setTitle(translation.getTranslation("embed-title-prefix") + translation.getTranslation("command-stats-error-region-embed-title"))
                    .setDescription(translation.getTranslation("command-stats-error-region-embed-description")
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
        embed.setAuthor(riotAccount.getRiotId(), Configs.getSettings().getWebsiteUri(), henrikAccount.getCard() != null ? henrikAccount.getCard().getSmall() : null);
        embed.setColor(209, 54, 57);
        embed.setTitle(translation.getTranslation("embed-title-prefix") + translation.getTranslation("command-stats-embed-title"));
        embed.setDescription(translation.getTranslation("command-stats-embed-description"));
        embed.addField(translation.getTranslation("command-stats-embed-field-1-name"), Emoji.getLevel().getFormatted() + " " + henrikAccount.getAccountLevel(), true);
        embed.addField(translation.getTranslation("command-stats-embed-field-2-name"), regionEmoji + " " + regionName, true);
        if(linkedAccount.isExisting()) {
            embed.addField(translation.getTranslation("command-stats-embed-field-3-name"),
                    Emoji.getDiscord().getFormatted() + " " + Bot.getJDA().getUserById(linkedAccount.getDiscordUserId()).getAsMention() + " (`" + Bot.getJDA().getUserById(linkedAccount.getDiscordUserId()).getAsTag() + "`)", true);
        }
        event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                Buttons.getRankButton(event.getGuild(), riotAccount)
        ).queue();
    }

    @Override
    public String getName() {
        return "Stats";
    }

    @Override
    public void register() {
        Bot.getJDA().upsertCommand(Commands.user(getName())).queue();
    }
}
