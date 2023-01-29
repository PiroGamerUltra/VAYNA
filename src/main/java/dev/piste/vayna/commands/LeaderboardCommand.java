package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.henrik.HenrikAPI;
import dev.piste.vayna.apis.henrik.HenrikApiException;
import dev.piste.vayna.apis.henrik.gson.HenrikAccount;
import dev.piste.vayna.apis.henrik.gson.MMRHistory;
import dev.piste.vayna.apis.riotgames.RiotAPI;
import dev.piste.vayna.apis.riotgames.gson.RiotAccount;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.embeds.ErrorEmbed;
import dev.piste.vayna.manager.Command;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Piste | https://github.com/zPiste
 */
public class LeaderboardCommand implements Command {


    @Override
    public void perform(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        if(event.getChannelType() != ChannelType.TEXT || event.getChannelType() == ChannelType.PRIVATE) {
            event.getHook().editOriginalEmbeds(ErrorEmbed.getNoGuildChannel(event.getUser())).setActionRow(
                    Button.link(Configs.getSettings().getSupportGuild().getInviteUri(), "Support").withEmoji(Emoji.getDiscord())
            ).queue();
            return;
        }

        Embed embed = new Embed();

        Map<User, MMRHistory> eloMap = new HashMap<>();

        for(Member member : event.getGuild().getMembers()) {
            LinkedAccount linkedAccount = new LinkedAccount(member.getUser().getIdLong());

            if(linkedAccount.isExisting() && linkedAccount.isVisibleToPublic()) {
                RiotAccount riotAccount = RiotAPI.getAccountByPuuid(linkedAccount.getRiotPuuid());
                try {
                    HenrikAccount henrikAccount = HenrikAPI.getAccountByRiotId(riotAccount.getGameName(), riotAccount.getTagLine());
                    MMRHistory mmrHistory = henrikAccount.getMmrHistory().get(0);
                    eloMap.put(member.getUser(), mmrHistory);
                } catch (HenrikApiException e) {
                    e.printStackTrace();
                }
            }
        }

        for(User user : eloMap.keySet()) {
            embed.addField(user.getAsTag(), eloMap.get(user).getCurrentTierPatched() + " : " + eloMap.get(user).getRankingInTier() + "RR", false);
        }

        event.getHook().editOriginalEmbeds(embed.build()).queue();
    }

    @Override
    public void register() {
        Bot.getJDA().upsertCommand(getName(), getDescription()).queue();
    }

    @Override
    public String getName() {
        return "leaderboard";
    }

    @Override
    public String getDescription() {
        return "Get a list of the best 20 players in this server";
    }
}
