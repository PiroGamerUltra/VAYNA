package dev.piste.vayna.commands;

import dev.piste.vayna.config.Config;
import dev.piste.vayna.config.ConfigSetting;
import dev.piste.vayna.manager.CommandManager;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class HelpCommand {

    public static void performCommand(SlashCommandInteractionEvent event) {

        String websiteUri = Config.readSetting(ConfigSetting.WEBSITE_URI);

        Embed embedBuilder = new Embed();
        embedBuilder.setTitle("» Help");
        embedBuilder.addField("Version", "The current version of the bot is `v" + Config.readSetting(ConfigSetting.VERSION) + "`", true);
        embedBuilder.addField("Creator", "I've been developed by **Piste#1111**", true);
        embedBuilder.addField("General Commands",  "» </help:" + CommandManager.findCommand("help").getId() + "> Retrieve general information about the bot and a list of all available commands", false);
        embedBuilder.addField("Connection Commands",  "» </connection connect:" + CommandManager.findSubcommand(CommandManager.findCommand("connection"), "connect").getId() + "> Connect your Riot-Games account with your Discord account\n" +
                "» </connection disconnect:" + CommandManager.findSubcommand(CommandManager.findCommand("connection"), "disconnect").getId() + "> Disconnect your currently connected Riot-Games account from your Discord account\n" +
                "» </connection info:" + CommandManager.findSubcommand(CommandManager.findCommand("connection"), "info").getId() + "> Retrieve information about the Riot-Games account that is currently connected with your Discord account", false);
        embedBuilder.setAuthor(event.getMember().getUser().getName(), websiteUri, event.getMember().getUser().getAvatarUrl());

        event.getHook().editOriginalEmbeds(embedBuilder.build()).setActionRow(
                Button.link(websiteUri + "redirect/discord", "Support Discord"),
                Button.link(websiteUri + "redirect/github", "GitHub"),
                Button.link(websiteUri + "redirect/topgg", "Top.GG")
        ).queue();

    }

}
