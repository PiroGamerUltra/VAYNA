package dev.piste.vayna.manager;

import dev.piste.vayna.Bot;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.util.Objects;

public class CommandManager {

    public static void createCommands() {

        Bot.getJDA().upsertCommand("help", "Get information about the bot and a list of all available commands").queue();
        SubcommandData connectSubcommandData = new SubcommandData("connect", "Connect your Riot-Games account with your Discord account");
        SubcommandData infoSubcommandData = new SubcommandData("info", "Get information about the Riot-Games account your Discord account is currently connected with");
        SubcommandData disconnectSubcommandData = new SubcommandData("disconnect", "Disconnect your currently connected Riot-Games account from your Discord account");
        Bot.getJDA().upsertCommand("connection", "Connection").addSubcommands(connectSubcommandData, infoSubcommandData, disconnectSubcommandData).queue();

    }

    public static Command findCommand(String query) {
        for(Command command : Bot.getJDA().retrieveCommands().complete()) {
            if(command.getName().equalsIgnoreCase(query)) return Objects.requireNonNull(command);
        }
        return null;
    }

    public static Command.Subcommand findSubcommand(Command command, String query) {
        for(Command.Subcommand subcommand : command.getSubcommands()) {
            if(subcommand.getName().equalsIgnoreCase(query)) return Objects.requireNonNull(subcommand);
        }
        return null;
    }



}
