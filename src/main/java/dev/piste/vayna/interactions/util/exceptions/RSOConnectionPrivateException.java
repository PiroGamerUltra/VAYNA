package dev.piste.vayna.interactions.util.exceptions;

import net.dv8tion.jda.api.entities.User;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class RSOConnectionPrivateException extends Exception {

    private final User target;

    public RSOConnectionPrivateException(User target) {
        this.target = target;
    }

    public User getTarget() {
        return target;
    }

}