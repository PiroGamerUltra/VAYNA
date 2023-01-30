package dev.piste.vayna.config.translations;

import dev.piste.vayna.config.translations.commands.Agent;
import dev.piste.vayna.config.translations.commands.Gamemode;
import dev.piste.vayna.config.translations.commands.Map;
import dev.piste.vayna.config.translations.commands.Weapon;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Commands {

    private Agent agent;
    private Gamemode gamemode;
    private Map map;
    private Weapon weapon;

    public Agent getAgent() {
        return agent;
    }

    public Gamemode getGamemode() {
        return gamemode;
    }

    public Map getMap() {
        return map;
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
