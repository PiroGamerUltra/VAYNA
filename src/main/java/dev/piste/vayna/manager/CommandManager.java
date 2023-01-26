package dev.piste.vayna.manager;

import dev.piste.vayna.Bot;
import dev.piste.vayna.api.valorantapi.Agent;
import dev.piste.vayna.api.valorantapi.Gamemode;
import dev.piste.vayna.api.valorantapi.Map;
import dev.piste.vayna.api.valorantapi.Weapon;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandManager {

    public static void createCommands() {

        List<String> commandList = new ArrayList<>();
        commandList.add("help");
        commandList.add("connection");
        commandList.add("stats");
        commandList.add("map");
        commandList.add("agent");
        commandList.add("gamemode");
        commandList.add("weapon");

        for(Command command : Bot.getJDA().retrieveCommands().complete()) {
            if(!commandList.contains(command.getName())) {
                command.delete().queue();
            }
        }

        for(String commandName : commandList) {
            createCommand(commandName);
        }

    }

    private static void createCommand(String commandName) {
        switch (commandName) {
            case "help" -> Bot.getJDA().upsertCommand("help", "Look up general information about the bot and a list of all available commands").queue();
            case "connection" -> Bot.getJDA().upsertCommand("connection", "Manage the connection to your Riot-Games account and its visibility").queue();
            case "stats" -> {
                SubcommandData userSub = new SubcommandData("user", "Get general information about a VALORANT profile from a Discord user")
                        .addOption(OptionType.USER, "user", "The discord user to get the stats from", true);
                SubcommandData riotIdSub = new SubcommandData("riot-id", "Get general information about a VALORANT profile by providing a Riot-ID")
                        .addOption(OptionType.STRING, "name", "The name of the Riot-ID (<name>#<tag>)", true)
                        .addOption(OptionType.STRING, "tag", "The tag of the Riot-ID (<name>#<tag>)", true);
                SubcommandData meSub = new SubcommandData("me", "Get general information about your VALORANT profile");
                Bot.getJDA().upsertCommand("stats", "Stats").addSubcommands(userSub, riotIdSub, meSub).queue();
            }
            case "map" -> {
                OptionData optionData = new OptionData(OptionType.STRING, "name", "Name of the map", true);
                for(Map map : Map.getMaps()) {
                    if(map.getDisplayName().equalsIgnoreCase("The Range")) continue;
                    optionData.addChoice(map.getDisplayName(), map.getDisplayName());
                }
                Bot.getJDA().upsertCommand("map", "Get information about a specific VALORANT map").addOptions(optionData).queue();
            }
            case "agent" -> {
                OptionData optionData = new OptionData(OptionType.STRING, "name", "Name of the agent", true);
                for(Agent agent : Agent.getAgents()) {
                    if(agent.getDeveloperName().equalsIgnoreCase("Hunter_NPE")) continue;
                    optionData.addChoice(agent.getDisplayName(), agent.getDisplayName());
                }
                Bot.getJDA().upsertCommand("agent", "Get information about a specific VALORANT agent").addOptions(optionData).queue();
            }
            case "gamemode" -> {
                OptionData optionData = new OptionData(OptionType.STRING, "name", "Name of the gamemode", true);
                for(Gamemode gamemode : Gamemode.getGamemodes()) {
                    if(gamemode.getDisplayName().equalsIgnoreCase("Onboarding") || gamemode.getDisplayName().equalsIgnoreCase("PRACTICE")) continue;
                    optionData.addChoice(gamemode.getDisplayName(), gamemode.getDisplayName());
                }
                Bot.getJDA().upsertCommand("gamemode", "Get information about a specific VALORANT gamemode").addOptions(optionData).queue();
            }
            case "weapon" -> {
                OptionData optionData = new OptionData(OptionType.STRING, "name", "Name of the weapon", true);
                for(Weapon weapon : Weapon.getWeapons()) {
                    optionData.addChoice(weapon.getDisplayName(), weapon.getDisplayName());
                }
                Bot.getJDA().upsertCommand("weapon", "Get information about a specific VALORANT weapon").addOptions(optionData).queue();
            }
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
