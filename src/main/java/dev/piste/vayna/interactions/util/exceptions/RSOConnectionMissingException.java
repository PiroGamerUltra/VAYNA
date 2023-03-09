package dev.piste.vayna.interactions.util.exceptions;

import net.dv8tion.jda.api.entities.User;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class RSOConnectionMissingException extends Exception {

    private final User target;

    public RSOConnectionMissingException(User target) {
        this.target = target;
    }

    public User getTarget() {
        return target;
    }
}