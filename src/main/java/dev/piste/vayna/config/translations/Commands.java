package dev.piste.vayna.config.translations;

import dev.piste.vayna.config.translations.commands.*;

/**
 * @author Piste | https://github.com/zPiste
 */
public class Commands {

    private Agent agent;
    private Gamemode gamemode;
    private Map map;
    private Weapon weapon;
    private Stats stats;
    private Leaderboard leaderboard;
    private Feedback feedback;
    private Store store;
    private Help help;

    public Commands() {
    }

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

    public Stats getStats() {
        return stats;
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public Store getStore() {
        return store;
    }

    public Help getHelp() {
        return help;
    }
}
