package dev.piste.vayna.buttons;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.henrik.HenrikAPI;
import dev.piste.vayna.apis.henrik.gson.HenrikAccount;
import dev.piste.vayna.apis.henrik.gson.mmr.Rank;
import dev.piste.vayna.apis.riotgames.RiotAPI;
import dev.piste.vayna.apis.riotgames.gson.RiotAccount;
import dev.piste.vayna.apis.valorantapi.ValorantAPI;
import dev.piste.vayna.apis.valorantapi.gson.CompetitiveTier;
import dev.piste.vayna.apis.valorantapi.gson.competitivetier.Tier;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.config.translations.Language;
import dev.piste.vayna.manager.Button;
import dev.piste.vayna.manager.ButtonManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class RankButton implements Button {

    public void perform(ButtonInteractionEvent event, String arg) throws StatusCodeException {
        event.deferReply().queue();

        Language language = Language.getLanguage(event.getGuild());

        RiotAccount oldRiotAccount = ButtonManager.getRiotAccountFromStatsButtonMap(arg);

        if(oldRiotAccount == null) {
            event.getHook().editOriginalEmbeds(language.getButtons().getRank().getErrors().getButtonTooOld().getMessageEmbed(event.getUser())).setActionRow(
                    language.getErrors().getSupportButton()
            ).queue();
            return;
        }

        RiotAccount riotAccount = RiotAPI.getAccountByPuuid(oldRiotAccount.getPuuid());

        HenrikAccount henrikAccount = HenrikAPI.getAccountByRiotId(riotAccount.getGameName(), riotAccount.getTagLine());

        Embed embed = new Embed()
                .setAuthor(riotAccount.getRiotId(), Configs.getSettings().getWebsiteUri(), henrikAccount.getCard().getSmall())
                .setTitle("» Rank")
                .setColor(209, 54, 57);

        Rank rank = henrikAccount.getMmr().getRank();

        CompetitiveTier competitiveTier = ValorantAPI.getLatestCompetitiveTier();
        if(rank.getCurrentTierPatched() == null) {
            Tier tier = competitiveTier.getTiers().get(0);
            embed.addField("Rank", Emoji.getRankByTierName(tier.getTierName()).getFormatted()  + " " + tier.getTierName(), true)
                    .setThumbnail(tier.getLargeIcon())
                    .addField("Games needed", "**" + rank.getGamesNeededForRating() + "** " + (rank.getGamesNeededForRating()==1 ? "game" : "games"), true);
            event.getHook().editOriginalEmbeds(embed.build()).queue();
            return;
        }
        for(Tier tier : competitiveTier.getTiers()) {
            if(tier.getTier() == rank.getCurrentTier()) {
                embed.addField("Rank", Emoji.getRankByTierName(tier.getTierName()).getFormatted() + " " + tier.getTierName(), false)
                        .setThumbnail(tier.getLargeIcon());
                if(rank.getCurrentTier() > 23) {
                    embed.addField("Rating", "**" + rank.getRankingInTier() + "**RR » " +
                            (rank.getMmrChangeToLastGame()>=0 ? Emoji.getIncrease().getFormatted() + " **+" + rank.getMmrChangeToLastGame() + "**" : Emoji.getDecrease().getFormatted() + " **" + rank.getMmrChangeToLastGame() + "**"), false);
                } else {
                    embed.addField("Rating", getProgressBar(rank.getRankingInTier()) + "\n" + "**" + rank.getRankingInTier() + "**/**100** » " +
                            (rank.getMmrChangeToLastGame()>=0 ? Emoji.getIncrease().getFormatted() + " **+" + rank.getMmrChangeToLastGame() + "**" : Emoji.getDecrease().getFormatted() + " **" + rank.getMmrChangeToLastGame() + "**"), false);
                }

                event.getHook().editOriginalEmbeds(embed.build()).queue();
                return;
            }
        }

    }

    @Override
    public String getName() {
        return "rank;";
    }

    private String getProgressBar(int ranking) {
        int value = (int) Math.round(ranking/10.0);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i<value; i++) {
            if(i==0) {
                stringBuilder.append(Emoji.getProgressBarGreen("start").getFormatted());
                continue;
            }
            if(i==9) {
                stringBuilder.append(Emoji.getProgressBarGreen("end").getFormatted());
                continue;
            }
            stringBuilder.append(Emoji.getProgressBarGreen("line").getFormatted());
        }
        for(int i = value; i<10; i++) {
            if(i==0) {
                stringBuilder.append(Emoji.getProgressBarRed("start").getFormatted());
                continue;
            }
            if(i==9) {
                stringBuilder.append(Emoji.getProgressBarRed("end").getFormatted());
                continue;
            }
            stringBuilder.append(Emoji.getProgressBarRed("line").getFormatted());
        }
        return stringBuilder.toString();
    }

}
