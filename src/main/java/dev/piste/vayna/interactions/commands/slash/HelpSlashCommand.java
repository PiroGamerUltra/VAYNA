package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.interactions.InteractionManager;
import dev.piste.vayna.interactions.util.interfaces.ISlashCommand;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.translations.LanguageManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.DiscordEmoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class HelpSlashCommand implements ISlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event, Language language) {
        event.deferReply(true).queue();

        Embed embed = new Embed()
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-help-embed-title"))
                .addField(language.getTranslation("command-help-embed-field-1-name"), language.getTranslation("command-help-embed-field-1-text")
                        .replaceAll("%version%", ConfigManager.getSettingsConfig().getVersion()), true);
        // General
        embed.addField(language.getTranslation("command-help-embed-field-2-name"),  "" +
                "» " + InteractionManager.getSlashCommandAsJdaCommand(new HelpSlashCommand()).getAsMention() + " " + new HelpSlashCommand().getDescription(language) + "\n" +
                "» " + InteractionManager.getSlashCommandAsJdaCommand(new FeedbackSlashCommand()).getAsMention() + " " + new FeedbackSlashCommand().getDescription(language), false);
        // /connection
        embed.addField(language.getTranslation("command-help-embed-field-3-name"),  "" +
                "» " + InteractionManager.getSlashCommandAsJdaCommand(new ConnectionSlashCommand()).getAsMention() + " " + new ConnectionSlashCommand().getDescription(language), false);
        // /stats & /leaderboard
        net.dv8tion.jda.api.interactions.commands.Command.Subcommand subcommand1 = InteractionManager.getSlashCommandAsJdaCommand(new StatsSlashCommand()).getSubcommands().get(0);
        net.dv8tion.jda.api.interactions.commands.Command.Subcommand subcommand2 = InteractionManager.getSlashCommandAsJdaCommand(new StatsSlashCommand()).getSubcommands().get(1);
        net.dv8tion.jda.api.interactions.commands.Command.Subcommand subcommand3 = InteractionManager.getSlashCommandAsJdaCommand(new StatsSlashCommand()).getSubcommands().get(2);
        embed.addField(language.getTranslation("command-help-embed-field-4-name"), "" +
                "» " + subcommand1.getAsMention() + " " + language.getTranslation("command-stats-user-desc") + "\n" +
                "» " + subcommand2.getAsMention() + " " + language.getTranslation("command-stats-riotid-desc") + "\n" +
                "» " + subcommand3.getAsMention() + " " + language.getTranslation("command-stats-me-desc") + "\n" +
                "» " + InteractionManager.getSlashCommandAsJdaCommand(new LeaderboardSlashCommand()).getAsMention() + " " + new LeaderboardSlashCommand().getDescription(language), false);
        // Info Commands
        embed.addField(language.getTranslation("command-help-embed-field-5-name"), "" +
                "» " + InteractionManager.getSlashCommandAsJdaCommand(new MapSlashCommand()).getAsMention() + " " + new MapSlashCommand().getDescription(language) + "\n" +
                "» " + InteractionManager.getSlashCommandAsJdaCommand(new AgentSlashCommand()).getAsMention() + " " + new AgentSlashCommand().getDescription(language) + "\n" +
                "» " + InteractionManager.getSlashCommandAsJdaCommand(new GameModeSlashCommand()).getAsMention() + " " + new GameModeSlashCommand().getDescription(language) + "\n" +
                "» " + InteractionManager.getSlashCommandAsJdaCommand(new WeaponSlashCommand()).getAsMention() + " " + new WeaponSlashCommand().getDescription(language) + "\n" +
                "» "  + InteractionManager.getSlashCommandAsJdaCommand(new StoreSlashCommand()).getAsMention() + " " + new StoreSlashCommand().getDescription(language), false);

        embed.addField(language.getTranslation("command-help-embed-field-6-name"), "" +
                "» " + InteractionManager.getSlashCommandAsJdaCommand(new SettingsSlashCommand()).getAsMention() + " " + new SettingsSlashCommand().getDescription(language), false);

        event.getHook().setEphemeral(true).editOriginalEmbeds(embed.build()).setActionRow(
                Button.link(ConfigManager.getSettingsConfig().getSupportGuildInviteUri(), language.getTranslation("button-support-name")).withEmoji(DiscordEmoji.DISCORD.getAsDiscordEmoji()),
                Button.link(ConfigManager.getSettingsConfig().getWebsiteUri() + "/redirect/github", "GitHub").withEmoji(DiscordEmoji.GITHUB.getAsDiscordEmoji()),
                Button.link(ConfigManager.getSettingsConfig().getWebsiteUri() + "/redirect/topgg", "Top.GG").withEmoji(DiscordEmoji.TOPGG.getAsDiscordEmoji())
        ).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription(Language language) {
        return language.getTranslation("command-help-desc");
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(getName(), getDescription(LanguageManager.getDefaultLanguage()));
    }

}
