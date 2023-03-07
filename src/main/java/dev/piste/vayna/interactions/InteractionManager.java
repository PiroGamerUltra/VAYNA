package dev.piste.vayna.interactions;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.interactions.buttons.IButton;
import dev.piste.vayna.interactions.buttons.DisconnectButton;
import dev.piste.vayna.interactions.buttons.HistoryButton;
import dev.piste.vayna.interactions.buttons.VisibilityButton;
import dev.piste.vayna.interactions.commands.context.StatsContextCommand;
import dev.piste.vayna.interactions.commands.context.IUserContextCommand;
import dev.piste.vayna.interactions.commands.slash.*;
import dev.piste.vayna.interactions.modals.FeedbackModal;
import dev.piste.vayna.interactions.modals.IModal;
import dev.piste.vayna.interactions.selectmenus.string.*;
import dev.piste.vayna.util.Logger;
import dev.piste.vayna.translations.LanguageManager;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class InteractionManager {

    private static final HashMap<String, ISlashCommand> slashCommands = new HashMap<>();
    private static final HashMap<String, IButton> buttons = new HashMap<>();
    private static final HashMap<String, IModal> modals = new HashMap<>();
    private static final HashMap<String, IStringSelectMenu> stringSelectMenus = new HashMap<>();
    private static final HashMap<String, IUserContextCommand> userContextCommands = new HashMap<>();

    public static void registerInteractions() {
        registerSlashCommands();
        registerButtons();
        registerModals();
        registerStringSelectMenus();
        registerUserContextCommands();
    }

    private static void registerSlashCommands() {
        registerSlashCommand(new AgentSlashCommand());
        registerSlashCommand(new ConnectionSlashCommand());
        registerSlashCommand(new GamemodeSlashCommand());
        registerSlashCommand(new HelpSlashCommand());
        registerSlashCommand(new MapSlashCommand());
        registerSlashCommand(new StatsSlashCommand());
        registerSlashCommand(new WeaponSlashCommand());
        registerSlashCommand(new StoreSlashCommand());
        registerSlashCommand(new LeaderboardSlashCommand());
        registerSlashCommand(new FeedbackSlashCommand());
        registerSlashCommand(new SettingsSlashCommand());
        registerSlashCommand(new StatusSlashCommand());
    }

    private static void registerButtons() {
        registerButton(new DisconnectButton());
        registerButton(new VisibilityButton());
        registerButton(new HistoryButton());
    }

    private static void registerModals() {
        registerModal(new FeedbackModal());
    }

    private static void registerStringSelectMenus() {
        registerStringSelectMenu(new SettingsSelectMenu());
        registerStringSelectMenu(new LanguageSelectMenu());
        registerStringSelectMenu(new BundleSelectMenu());
    }

    private static void registerUserContextCommands() {
        registerUserContextCommand(new StatsContextCommand());
    }

    private static void registerSlashCommand(ISlashCommand slashCommand) {
        slashCommands.put(slashCommand.getName(), slashCommand);
        try {
            Bot.getJDA().upsertCommand(slashCommand.getCommandData()).queue();
        } catch (HttpErrorException | IOException | InterruptedException e) {
            new Logger(InteractionManager.class).error("Register command HTTP error", e);
        }
    }

    private static void registerButton(IButton button) {
        buttons.put(button.getName(), button);
    }

    private static void registerModal(IModal modal) {
        modals.put(modal.getName(), modal);
    }

    private static void registerStringSelectMenu(IStringSelectMenu stringSelectMenu) {
        stringSelectMenus.put(stringSelectMenu.getName(), stringSelectMenu);
    }

    private static void registerUserContextCommand(IUserContextCommand userContextCommand) {
        userContextCommands.put(userContextCommand.getName(), userContextCommand);
        Bot.getJDA().upsertCommand(userContextCommand.getCommandData()).queue();
    }

    public static Command getSlashCommandAsJdaCommand(ISlashCommand slashCommand) {
        for(Command jdaCommand : Bot.getJDA().retrieveCommands().complete()) {
            if(jdaCommand.getName().equalsIgnoreCase(slashCommand.getName())) return jdaCommand;
        }
        return null;
    }

    public static void perform(GenericEvent event) throws IOException, HttpErrorException, InterruptedException {
        if(event instanceof SlashCommandInteractionEvent interactionEvent) {
            slashCommands.get(interactionEvent.getName()).perform(interactionEvent, LanguageManager.getLanguage(interactionEvent.getGuild()));
        } else if(event instanceof ButtonInteractionEvent interactionEvent) {
            String buttonId = interactionEvent.getButton().getId().split(";")[0];
            String[] args = interactionEvent.getButton().getId().contains(";") ? interactionEvent.getButton().getId().substring(buttonId.length()+1).split(";") : new String[0];
            buttons.get(buttonId).perform(interactionEvent, args, LanguageManager.getLanguage(interactionEvent.getGuild()));
        } else if(event instanceof ModalInteractionEvent interactionEvent) {
            modals.get(interactionEvent.getModalId()).perform(interactionEvent, LanguageManager.getLanguage(interactionEvent.getGuild()));
        } else if(event instanceof StringSelectInteractionEvent interactionEvent) {
            stringSelectMenus.get(interactionEvent.getSelectMenu().getId()).perform(interactionEvent, LanguageManager.getLanguage(interactionEvent.getGuild()));
        } else if(event instanceof UserContextInteractionEvent interactionEvent) {
            userContextCommands.get(interactionEvent.getName()).perform(interactionEvent, LanguageManager.getLanguage(interactionEvent.getGuild()));
        }
    }

}