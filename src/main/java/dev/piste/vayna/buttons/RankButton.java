package dev.piste.vayna.buttons;

import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.henrik.HenrikAPI;
import dev.piste.vayna.apis.henrik.gson.HenrikAccount;
import dev.piste.vayna.apis.henrik.gson.mmr.Rank;
import dev.piste.vayna.apis.riotgames.RiotAPI;
import dev.piste.vayna.apis.riotgames.gson.RiotAccount;
import dev.piste.vayna.apis.valorantapi.ValorantAPI;
import dev.piste.vayna.apis.valorantapi.gson.competitivetier.Tier;
import dev.piste.vayna.manager.Button;
import dev.piste.vayna.manager.ButtonManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import dev.piste.vayna.util.buttons.Buttons;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.util.ArrayList;

public class RankButton implements Button {

    public void perform(ButtonInteractionEvent event, String arg) throws StatusCodeException {
        event.deferReply().queue();
        Language language = LanguageManager.getLanguage(event.getGuild());

        RiotAccount oldRiotAccount = ButtonManager.getRiotAccountFromStatsButtonMap(arg);

        // If the button is too old
        if(oldRiotAccount == null) {
            Embed embed = new Embed()
                    .setAuthor(event.getUser().getName(), event.getUser().getAvatarUrl())
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
        ArrayList<Tier> tiers = ValorantAPI.getCompetitiveTier(language.getLanguageCode()).getTiers();

        Embed embed = new Embed()
                .setAuthor(riotAccount.getRiotId(), henrikAccount.getCard().getSmall())
                .setTitle(language.getTranslation("embed-title-prefix") + language.getTranslation("button-rank-embed-title"))
                .setColor(209, 54, 57);

        if(rank.getCurrentTierPatched() == null) {
            Tier tier = tiers.get(0);
            embed.setThumbnail(tier.getLargeIcon())
                    .addField(language.getTranslation("button-rank-embed-field-1-name"), Emoji.getRankByTierName(tier.getTier()).getFormatted()  + " " + tier.getTierName(), true)
                    .addField(language.getTranslation("button-rank-embed-field-2-name"),
                    "**" + rank.getGamesNeededForRating() + "** " + (rank.getGamesNeededForRating()==1 ? language.getTranslation("button-rank-embed-field-2-text-1") : language.getTranslation("button-rank-embed-field-2-text-2")), true);
        } else {
            for (Tier tier : tiers) {
                if (tier.getTier() == rank.getCurrentTier()) {
                    embed.addField(language.getTranslation("button-rank-embed-field-1-name"), Emoji.getRankByTierName(tier.getTier()).getFormatted() + " " + tier.getTierName(), true)
                            .setThumbnail(tier.getLargeIcon());
                    if (rank.getCurrentTier() > 23) {
                        embed.addField(language.getTranslation("button-rank-embed-field-3-name"), "**" + rank.getRankingInTier() + "**RR » " +
                                (rank.getMmrChangeToLastGame() >= 0 ? Emoji.getIncrease().getFormatted() + " **+" + rank.getMmrChangeToLastGame() + "**" : Emoji.getDecrease().getFormatted() + " **" + rank.getMmrChangeToLastGame() + "**"), false);
                    } else {
                        embed.addField(language.getTranslation("button-rank-embed-field-3-name"), getProgressBar(rank.getRankingInTier()) + "\n" + "**" + rank.getRankingInTier() + "**/**100** » " +
                                (rank.getMmrChangeToLastGame() >= 0 ? Emoji.getIncrease().getFormatted() + " **+" + rank.getMmrChangeToLastGame() + "**" : Emoji.getDecrease().getFormatted() + " **" + rank.getMmrChangeToLastGame() + "**"), false);
                    }
                }
            }
        }
        // Reply
        event.getHook().editOriginalEmbeds(embed.build()).queue();
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
