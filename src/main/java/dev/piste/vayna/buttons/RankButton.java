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
import dev.piste.vayna.util.buttons.Buttons;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class RankButton implements Button {

    public void perform(ButtonInteractionEvent event, String arg) throws StatusCodeException {
        event.deferReply().queue();

        Language language = Language.getLanguage(event.getGuild());

        RiotAccount oldRiotAccount = ButtonManager.getRiotAccountFromStatsButtonMap(arg);

        if(oldRiotAccount == null) {
            event.getHook().editOriginalEmbeds(language.getButtons().getRank().getErrors().getButtonTooOld().getMessageEmbed(event.getUser())).setActionRow(
                    Buttons.getSupportButton(event.getGuild())
            ).queue();
            return;
        }

        RiotAccount riotAccount = RiotAPI.getAccountByPuuid(oldRiotAccount.getPuuid());

        HenrikAccount henrikAccount = HenrikAPI.getAccountByRiotId(riotAccount.getGameName(), riotAccount.getTagLine());

        Rank rank = henrikAccount.getMmr().getRank();

        CompetitiveTier competitiveTier = ValorantAPI.getLatestCompetitiveTier(language.getLanguageCode());
        CompetitiveTier enUsCompetitiveTier = ValorantAPI.getLatestCompetitiveTier("en-US");

        if(rank.getCurrentTierPatched() == null) {
            Tier tier = competitiveTier.getTiers().get(0);
            Tier enUsTier = enUsCompetitiveTier.getTiers().get(0);
            event.getHook().editOriginalEmbeds(language.getButtons().getRank().getUnrankedMessageEmbed(riotAccount, henrikAccount, tier, enUsTier, rank)).queue();
            return;
        }
        for(int i = 0; i < competitiveTier.getTiers().size(); i++) {
            Tier tier = competitiveTier.getTiers().get(i);
            if(tier.getTier() == rank.getCurrentTier()) {
                Tier enUsTier = enUsCompetitiveTier.getTiers().get(i);
                event.getHook().editOriginalEmbeds(language.getButtons().getRank().getUnrankedMessageEmbed(riotAccount, henrikAccount, tier, enUsTier, rank)).queue();
                return;
            }
        }
    }

    @Override
    public String getName() {
        return "rank;";
    }

}
