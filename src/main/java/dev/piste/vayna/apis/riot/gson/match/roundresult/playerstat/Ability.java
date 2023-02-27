package dev.piste.vayna.apis.riot.gson.match.roundresult.playerstat;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Ability {

    private String grenadeEffects;
    private String ability1Effects;
    private String ability2Effects;
    private String ultimateEffects;

    public String getGrenadeEffects() {
        return grenadeEffects;
    }

    public String getAbility1Effects() {
        return ability1Effects;
    }

    public String getAbility2Effects() {
        return ability2Effects;
    }

    public String getUltimateEffects() {
        return ultimateEffects;
    }
}
