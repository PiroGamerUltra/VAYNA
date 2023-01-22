package dev.piste.vayna.commands;

import dev.piste.vayna.config.SettingsConfig;
import dev.piste.vayna.manager.CommandManager;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class HelpCommand {

    public static void performCommand(SlashCommandInteractionEvent event) {

        SettingsConfig settingsConfig = new SettingsConfig();

        Embed embedBuilder = new Embed();
        embedBuilder.setTitle("» Help");
        embedBuilder.addField("Version", "The current version of the bot is `v" + settingsConfig.getVersion() + "`", true);
        embedBuilder.addField("Creator", "I've been developed by **Piste#1111**", true);
        embedBuilder.addField("General Commands",  "» </help:" + CommandManager.findCommand("help").getId() + "> Retrieve general information about the bot and a list of all available commands", false);
        embedBuilder.addField("Connection Commands",  "» </connection connect:" + CommandManager.findSubcommand(CommandManager.findCommand("connection"), "connect").getId() + "> Connect your Riot-Games account with your Discord account\n" +
                "» </connection disconnect:" + CommandManager.findSubcommand(CommandManager.findCommand("connection"), "disconnect").getId() + "> Disconnect your currently connected Riot-Games account from your Discord account\n" +
                "» </connection info:" + CommandManager.findSubcommand(CommandManager.findCommand("connection"), "info").getId() + "> Retrieve information about the Riot-Games account that is currently connected with your Discord account", false);

        event.getHook().editOriginalEmbeds(embedBuilder.build()).setActionRow(
                Button.link(settingsConfig.getWebsiteUri() + "/redirect/discord", "Support Discord"),
                Button.link(settingsConfig.getWebsiteUri() + "/redirect/github", "GitHub"),
                Button.link(settingsConfig.getWebsiteUri() + "/redirect/topgg", "Top.GG")
        ).queue();

    }

}
