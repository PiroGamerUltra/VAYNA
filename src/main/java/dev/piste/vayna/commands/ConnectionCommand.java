package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.manager.Command;
import dev.piste.vayna.api.riotgames.RiotAccount;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.counter.StatsCounter;
import dev.piste.vayna.embeds.ConnectionEmbed;
import dev.piste.vayna.mongodb.AuthKey;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class ConnectionCommand implements Command {

    @Override
    public void perform(SlashCommandInteractionEvent event) {
        event.deferReply().setEphemeral(true).queue();

        StatsCounter.countConnections();

        LinkedAccount linkedAccount = new LinkedAccount(event.getUser().getIdLong());

        if(!linkedAccount.isExisting()) {
            String authKey = new AuthKey(event.getUser().getIdLong()).getAuthKey();

            event.getHook().editOriginalEmbeds(ConnectionEmbed.getNoConnectionPresent(event.getUser().getAsMention())).setActionRow(
                    Button.link(Configs.getSettings().getWebsiteUri() + "/RSO/redirect/?authKey=" + authKey, "Connect").withEmoji(Emoji.getRiotGames())
            ).queue();
        } else {
            RiotAccount riotAccount = RiotAccount.getByPuuid(linkedAccount.getRiotPuuid());

            String buttonId = linkedAccount.isVisibleToPublic() ? "private" : "public";
            String emojiUnicode = linkedAccount.isVisibleToPublic() ? "\uD83D\uDD12" : "\uD83D\uDD13";

            event.getHook().editOriginalEmbeds(ConnectionEmbed.getConnectionPresent(riotAccount.getRiotId(), event.getUser().getAsMention(), linkedAccount.isVisibleToPublic())).setActionRow(
                    Button.danger("connection;disconnect", "Disconnect"),
                    Button.secondary("connection;" + buttonId, "Change visibility").withEmoji(net.dv8tion.jda.api.entities.emoji.Emoji.fromUnicode(emojiUnicode))
            ).queue();
        }
    }

    @Override
    public void register() {
        Bot.getJDA().upsertCommand(getName(), getDescription()).queue();
    }

    @Override
    public String getName() {
        return "connection";
    }

    @Override
    public String getDescription() {
        return "Manage the connection to your Riot-Games account and its visibility";
    }
}
