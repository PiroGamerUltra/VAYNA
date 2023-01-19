package dev.piste.vayna.manager;

import dev.piste.vayna.Bot;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandManager {

    public static void createCommands() {

        List<String> commandList = new ArrayList<>();
        commandList.add("help");
        commandList.add("connection");

        for(Command command : Bot.getJDA().retrieveCommands().complete()) {
            for(String commandName : commandList) {
                if(!command.getName().equalsIgnoreCase(commandName)) {
                    Bot.getJDA().deleteCommandById(command.getIdLong());
                }
            }
        }

        for(String commandName : commandList) {
            createCommand(commandName);
        }

    }

    private static void createCommand(String commandName) {
        switch (commandName) {
            case "help":
                Bot.getJDA().upsertCommand("help", "Get information about the bot and a list of all available commands").queue();
                break;
            case "connection":
                SubcommandData connectSub = new SubcommandData("connect", "Connect your Riot-Games account with your Discord account");
                SubcommandData infoSub = new SubcommandData("info", "Get information about the Riot-Games account your Discord account is currently connected with");
                SubcommandData disconnectSub = new SubcommandData("disconnect", "Disconnect your currently connected Riot-Games account from your Discord account");
                Bot.getJDA().upsertCommand("connection", "Connection").addSubcommands(connectSub, disconnectSub, infoSub).queue();
                break;
        }
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
