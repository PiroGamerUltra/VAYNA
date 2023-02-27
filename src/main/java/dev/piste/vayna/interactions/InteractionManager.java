package dev.piste.vayna.interactions;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.interactions.buttons.Button;
import dev.piste.vayna.interactions.buttons.DisconnectButton;
import dev.piste.vayna.interactions.buttons.HistoryButton;
import dev.piste.vayna.interactions.buttons.VisibilityButton;
import dev.piste.vayna.interactions.commands.context.StatsContextCommand;
import dev.piste.vayna.interactions.commands.context.UserContextCommand;
import dev.piste.vayna.interactions.commands.slash.*;
import dev.piste.vayna.interactions.modals.FeedbackModal;
import dev.piste.vayna.interactions.modals.Modal;
import dev.piste.vayna.interactions.selectmenus.string.*;
import dev.piste.vayna.util.Logger;
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

    private static final HashMap<String, SlashCommand> slashCommands = new HashMap<>();
    private static final HashMap<String, Button> buttons = new HashMap<>();
    private static final HashMap<String, Modal> modals = new HashMap<>();
    private static final HashMap<String, StringSelectMenu> stringSelectMenus = new HashMap<>();
    private static final HashMap<String, UserContextCommand> userContextCommands = new HashMap<>();

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
        registerStringSelectMenu(new HistorySelectMenu());
        registerStringSelectMenu(new BundleSelectMenu());
    }

    private static void registerUserContextCommands() {
        registerUserContextCommand(new StatsContextCommand());
    }

    private static void registerSlashCommand(SlashCommand slashCommand) {
        slashCommands.put(slashCommand.getName(), slashCommand);
        try {
            Bot.getJDA().upsertCommand(slashCommand.getCommandData()).queue();
        } catch (HttpErrorException | IOException | InterruptedException e) {
            new Logger(InteractionManager.class).error("Register command HTTP error", e);
        }
    }

    private static void registerButton(Button button) {
        buttons.put(button.getName(), button);
    }

    private static void registerModal(Modal modal) {
        modals.put(modal.getName(), modal);
    }

    private static void registerStringSelectMenu(StringSelectMenu stringSelectMenu) {
        stringSelectMenus.put(stringSelectMenu.getName(), stringSelectMenu);
    }

    private static void registerUserContextCommand(UserContextCommand userContextCommand) {
        userContextCommands.put(userContextCommand.getName(), userContextCommand);
        Bot.getJDA().upsertCommand(userContextCommand.getCommandData()).queue();
    }

    public static Command getSlashCommandAsJdaCommand(SlashCommand slashCommand) {
        for(Command jdaCommand : Bot.getJDA().retrieveCommands().complete()) {
            if(jdaCommand.getName().equalsIgnoreCase(slashCommand.getName())) return jdaCommand;
        }
        return null;
    }

    public static void perform(GenericEvent event) throws IOException, HttpErrorException, InterruptedException {
        if(event instanceof SlashCommandInteractionEvent) {
            slashCommands.get(((SlashCommandInteractionEvent) event).getName()).perform((SlashCommandInteractionEvent) event);
        } else if(event instanceof ButtonInteractionEvent) {
            String buttonId = ((ButtonInteractionEvent) event).getButton().getId().split(";")[0];
            String[] args = ((ButtonInteractionEvent) event).getButton().getId().substring(buttonId.length()).split(";");
            buttons.get(buttonId).perform((ButtonInteractionEvent) event, args);
        } else if(event instanceof ModalInteractionEvent) {
            modals.get(((ModalInteractionEvent) event).getModalId()).perform((ModalInteractionEvent) event);
        } else if(event instanceof StringSelectInteractionEvent) {
            stringSelectMenus.get(((StringSelectInteractionEvent) event).getSelectMenu().getId()).perform((StringSelectInteractionEvent) event);
        } else if(event instanceof UserContextInteractionEvent) {
            userContextCommands.get(((UserContextInteractionEvent) event).getName()).perform((UserContextInteractionEvent) event);
        }
    }

}
