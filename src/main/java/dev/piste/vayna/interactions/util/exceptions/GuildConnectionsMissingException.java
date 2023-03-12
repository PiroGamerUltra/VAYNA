package dev.piste.vayna.interactions.util.exceptions;

import net.dv8tion.jda.api.entities.Guild;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class GuildConnectionsMissingException extends Exception {

    private final Guild guild;

    public GuildConnectionsMissingException(Guild guild) {
        this.guild = guild;
    }

    public Guild getGuild() {
        return guild;
    }

}