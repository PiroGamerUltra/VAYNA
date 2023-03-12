package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.http.HttpErrorException;
import dev.piste.vayna.interactions.general.SettingsInteraction;
import dev.piste.vayna.interactions.util.exceptions.InsufficientPermissionException;
import dev.piste.vayna.interactions.util.interfaces.ISlashCommand;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.translations.LanguageManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class SettingsSlashCommand implements ISlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event, Language language) throws HttpErrorException, InsufficientPermissionException {
        event.deferReply(true).queue();

        if(!event.getMember().hasPermission(Permission.MANAGE_SERVER)) throw new InsufficientPermissionException(Permission.MANAGE_SERVER);

        SettingsInteraction.sendSettingsEmbed(event.getHook(), language);
    }

    @Override
    public String getName() {
        return "settings";
    }

    @Override
    public String getDescription(Language language) {
        return language.getTranslation("command-settings-desc");
    }

    @Override
    public CommandData getCommandData() throws HttpErrorException {
        return Commands.slash(getName(), getDescription(LanguageManager.getDefaultLanguage())).setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER));
    }

}