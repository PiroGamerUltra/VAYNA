package dev.piste.vayna.interactions.managers;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.interactions.commands.context.StatsContextCommand;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class UserContextCommandManager {

    private static final HashMap<String, UserContextCommand> userContextCommands = new HashMap<>();

    public static void registerStringSelectMenus() {
        addUserContextCommand(new StatsContextCommand());
    }

    private static void addUserContextCommand(UserContextCommand userContextCommand) {
        userContextCommands.put(userContextCommand.getName(), userContextCommand);
        userContextCommand.register();
    }

    public static void perform(UserContextInteractionEvent event) throws HttpErrorException, IOException, InterruptedException {
        userContextCommands.get(event.getName()).perform(event);
    }

}
