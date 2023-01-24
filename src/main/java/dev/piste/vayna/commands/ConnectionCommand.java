package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.api.riotgames.RiotAccount;
import dev.piste.vayna.config.SettingsConfig;
import dev.piste.vayna.embeds.ConnectionEmbed;
import dev.piste.vayna.mongodb.AuthKey;
import dev.piste.vayna.mongodb.LinkedAccount;
import dev.piste.vayna.mongodb.Mongo;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class ConnectionCommand {

    public static void performCommand(SlashCommandInteractionEvent event) {

        LinkedAccount linkedAccount = new LinkedAccount(event.getUser().getIdLong());

        countConnections();

        if(!linkedAccount.isExisting()) {
            // Account hasn't been found in the database
            String authKey = new AuthKey(event.getUser().getIdLong()).getAuthKey();

            // Sending reply
            event.getHook().editOriginalEmbeds(ConnectionEmbed.getNoConnectionPresent(event.getUser().getAsMention())).setActionRow(
                    Button.link(SettingsConfig.getWebsiteUri() + "/RSO/redirect/?authKey=" + authKey, "Connect").withEmoji(Emoji.getRiotGames())
            ).queue();
        } else {
            // Account has been found in the database
            RiotAccount riotAccount = null;
            riotAccount = new RiotAccount(linkedAccount.getRiotPuuid());

            // Sending reply
            event.getHook().editOriginalEmbeds(ConnectionEmbed.getConnectionPresent(riotAccount.getRiotId(), event.getUser().getAsMention())).setActionRow(
                    Button.danger("/connection;disconnect", "Disconnect")
            ).queue();
        }

    }

    public static void countConnections() {
        if(Bot.isDebug()) return;
        Guild supportGuild = Bot.getJDA().getGuildById(SettingsConfig.getSupportGuildId());
        String connectionCountChannelName = SettingsConfig.getConnectionCountChannelName().replace("%count%", Mongo.getLinkedAccountCollection().countDocuments() + "");
        VoiceChannel connectionCountChannel = supportGuild.getVoiceChannelById(SettingsConfig.getConnectionCountChannelId());
        connectionCountChannel.getManager().setName(connectionCountChannelName).queue();
    }

}
