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
import dev.piste.vayna.manager.Button;
import dev.piste.vayna.manager.ButtonManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import dev.piste.vayna.util.Language;
import dev.piste.vayna.util.LanguageManager;
import dev.piste.vayna.util.buttons.Buttons;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class RankButton implements Button {

    public void perform(ButtonInteractionEvent event, String arg) throws StatusCodeException {
        event.deferReply().queue();

        Language language = LanguageManager.getLanguage(event.getGuild());

        RiotAccount oldRiotAccount = ButtonManager.getRiotAccountFromStatsButtonMap(arg);

        if(oldRiotAccount == null) {
            Embed embed = new Embed().setAuthor(event.getUser().getName(), Configs.getSettings().getWebsiteUri(), event.getUser().getAvatarUrl())
                    .setColor(255, 0, 0)
                    .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("button-rank-error-old-embed-title"))
                    .setDescription(language.getTranslation("button-rank-error-old-embed-description"));
            event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                    Buttons.getSupportButton(event.getGuild())
            ).queue();
            return;
        }

        RiotAccount riotAccount = RiotAPI.getAccountByPuuid(oldRiotAccount.getPuuid());
        HenrikAccount henrikAccount = HenrikAPI.getAccountByRiotId(riotAccount.getGameName(), riotAccount.getTagLine());
        Rank rank = henrikAccount.getMmr().getRank();
        CompetitiveTier competitiveTier = ValorantAPI.getCompetitiveTier(language.getLanguageCode());
        CompetitiveTier enUsCompetitiveTier = ValorantAPI.getCompetitiveTier("en-US");

        Embed embed = new Embed()
                .setAuthor(riotAccount.getRiotId(), Configs.getSettings().getWebsiteUri(), henrikAccount.getCard().getSmall())
                .setTitle(language.getTranslation("embed-title-prefix") + language.getTranslation("button-rank-embed-title"))
                .setColor(209, 54, 57);

        if(rank.getCurrentTierPatched() == null) {
            Tier tier = competitiveTier.getTiers().get(0);
            Tier enUsTier = enUsCompetitiveTier.getTiers().get(0);

            embed.addField(language.getTranslation("button-rank-embed-field-1-name"), Emoji.getRankByTierName(enUsTier.getTierName()).getFormatted()  + " " + tier.getTierName(), true);
            embed.addField(language.getTranslation("button-rank-embed-field-2-name"),
                    "**" + rank.getGamesNeededForRating() + "** " + (rank.getGamesNeededForRating()==1 ? language.getTranslation("button-rank-embed-field-2-text-1") : language.getTranslation("button-rank-embed-field-2-text-2")), true);
            embed.setThumbnail(tier.getLargeIcon());
            event.getHook().editOriginalEmbeds(embed.build()).queue();
            return;
        }
        for(int i = 0; i < competitiveTier.getTiers().size(); i++) {
            Tier tier = competitiveTier.getTiers().get(i);
            if(tier.getTier() == rank.getCurrentTier()) {
                Tier enUsTier = enUsCompetitiveTier.getTiers().get(i);

                embed.addField(language.getTranslation("button-rank-embed-field-1-name"), Emoji.getRankByTierName(enUsTier.getTierName()).getFormatted()  + " " + tier.getTierName(), true);
                if(rank.getCurrentTier() > 23) {
                    embed.addField(language.getTranslation("button-rank-embed-field-3-name"), "**" + rank.getRankingInTier() + "**RR » " +
                            (rank.getMmrChangeToLastGame()>=0 ? Emoji.getIncrease().getFormatted() + " **+" + rank.getMmrChangeToLastGame() + "**" : Emoji.getDecrease().getFormatted() + " **" + rank.getMmrChangeToLastGame() + "**"), false);
                } else {
                    embed.addField(language.getTranslation("button-rank-embed-field-3-name"), getProgressBar(rank.getRankingInTier()) + "\n" + "**" + rank.getRankingInTier() + "**/**100** » " +
                            (rank.getMmrChangeToLastGame()>=0 ? Emoji.getIncrease().getFormatted() + " **+" + rank.getMmrChangeToLastGame() + "**" : Emoji.getDecrease().getFormatted() + " **" + rank.getMmrChangeToLastGame() + "**"), false);
                }
                embed.setThumbnail(tier.getLargeIcon());

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
