package dev.piste.vayna.interactions.util.exceptions;

import net.dv8tion.jda.api.Permission;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class InsufficientPermissionException extends Exception {

    private final Permission permission;

    public InsufficientPermissionException(Permission permission) {
        this.permission = permission;
    }

    public Permission getPermission() {
        return permission;
    }
}