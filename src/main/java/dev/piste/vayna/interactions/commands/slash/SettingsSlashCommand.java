package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.MessageEmbeds;
import dev.piste.vayna.util.templates.SelectMenus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class SettingsSlashCommand implements SlashCommand {


    @Override
    public void perform(SlashCommandInteractionEvent event) throws HttpErrorException {
        event.deferReply(true).queue();

        if(!event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
            Language language = LanguageManager.getLanguage(event.getGuild());

            Embed embed = new Embed()
                    .setAuthor(event.getUser().getName(), event.getUser().getAvatarUrl())
                    .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-settings-error-perms-embed-title"))
                    .setDescription(language.getTranslation("command-settings-error-perms-embed-description"))
                    .setColor(255, 0, 0);
            event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                    Buttons.getSupportButton(language)
            ).queue();
            return;
        }

        event.getHook().editOriginalEmbeds(MessageEmbeds.getSettingsEmbed(LanguageManager.getLanguage(event.getGuild()), event.getGuild())).setActionRow(
                SelectMenus.getSettingsSelectMenu(event.getGuild())
        ).queue();

    }

    @Override
    public CommandData getCommandData() throws HttpErrorException {
        return Commands.slash(getName(), getDescription()).setGuildOnly(true).setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER));
    }

    @Override
    public String getName() {
        return "settings";
    }

    @Override
    public String getDescription() {
        return LanguageManager.getLanguage().getTranslation("command-settings-description");
    }
}
