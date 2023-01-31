package dev.piste.vayna.config.translations.commands;

import dev.piste.vayna.commands.*;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.manager.CommandManager;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Help {

    private String help;
    private String version;
    private String versionDesc;
    private String general;
    private String connection;
    private String statistic;
    private String information;

    public MessageEmbed getMessageEmbed(User user) {
        Embed embedBuilder = new Embed();
        embedBuilder.setAuthor(user.getName(), Configs.getSettings().getWebsiteUri(), user.getAvatarUrl());
        embedBuilder.setTitle(Configs.getTranslations().getTitlePrefix() + help);
        embedBuilder.addField(version, versionDesc.replaceAll("%version%", Configs.getSettings().getVersion()), true);
        // General
        embedBuilder.addField(general,  "" +
                "» " + CommandManager.getAsJdaCommand(new HelpCommand()).getAsMention() + " " + CommandManager.getAsJdaCommand(new HelpCommand()).getDescription() + "\n" +
                "» " + CommandManager.getAsJdaCommand(new FeedbackCommand()).getAsMention() + " " + CommandManager.getAsJdaCommand(new FeedbackCommand()).getDescription(), false);
        // /connection
        embedBuilder.addField(connection,  "" +
                "» " + CommandManager.getAsJdaCommand(new ConnectionCommand()).getAsMention() + " " + CommandManager.getAsJdaCommand(new ConnectionCommand()).getDescription(), false);
        // /stats & /leaderboard
        net.dv8tion.jda.api.interactions.commands.Command.Subcommand subcommand1 = CommandManager.getAsJdaCommand(new StatsCommand()).getSubcommands().get(0);
        net.dv8tion.jda.api.interactions.commands.Command.Subcommand subcommand2 = CommandManager.getAsJdaCommand(new StatsCommand()).getSubcommands().get(1);
        net.dv8tion.jda.api.interactions.commands.Command.Subcommand subcommand3 = CommandManager.getAsJdaCommand(new StatsCommand()).getSubcommands().get(2);
        embedBuilder.addField(statistic, "" +
                "» " + subcommand1.getAsMention() + " " + subcommand1.getDescription() + "\n" +
                "» " + subcommand2.getAsMention() + " " + subcommand2.getDescription() + "\n" +
                "» " + subcommand3.getAsMention() + " " + subcommand3.getDescription() + "\n" +
                "» " + CommandManager.getAsJdaCommand(new LeaderboardCommand()).getAsMention() + " " + CommandManager.getAsJdaCommand(new LeaderboardCommand()).getDescription(), false);
        // Info Commands
        embedBuilder.addField(information, "" +
                "» " + CommandManager.getAsJdaCommand(new MapCommand()).getAsMention() + " " + CommandManager.getAsJdaCommand(new MapCommand()).getDescription() + "\n" +
                "» " + CommandManager.getAsJdaCommand(new AgentCommand()).getAsMention() + " " + CommandManager.getAsJdaCommand(new AgentCommand()).getDescription() + "\n" +
                "» " + CommandManager.getAsJdaCommand(new GamemodeCommand()).getAsMention() + " " + CommandManager.getAsJdaCommand(new GamemodeCommand()).getDescription() + "\n" +
                "» " + CommandManager.getAsJdaCommand(new WeaponCommand()).getAsMention() + " " + CommandManager.getAsJdaCommand(new WeaponCommand()).getDescription() + "\n" +
                "» "  + CommandManager.getAsJdaCommand(new StoreCommand()).getAsMention() + " " + CommandManager.getAsJdaCommand(new StoreCommand()).getDescription(), false);
        return embedBuilder.build();
    }

}
