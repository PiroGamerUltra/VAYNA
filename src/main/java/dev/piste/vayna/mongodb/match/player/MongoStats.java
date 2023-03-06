package dev.piste.vayna.mongodb.match.player;

import dev.piste.vayna.apis.riot.gson.match.player.Stats;
import org.bson.Document;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class MongoStats {

    private static final String SCORE_FIELD = "score";
    private static final String ROUNDS_PLAYED_FIELD = "roundsPlayed";
    private static final String KILLS_FIELD = "kills";
    private static final String DEATHS_FIELD = "deaths";
    private static final String ASSISTS_FIELD = "assists";
    private static final String C_ABILITY_CASTS_FIELD = "cAbilityCasts";
    private static final String Q_ABILITY_CASTS = "qAbilityCasts";
    private static final String E_ABILITY_CASTS = "eAbilityCasts";
    private static final String ULTIMATE_CASTS = "ultimateCasts";

    private final Document document;

    private final int score;
    private final int roundsPlayed;
    private final int kills;
    private final int deaths;
    private final int assists;
    private final int cAbilityCasts;
    private final int qAbilityCasts;
    private final int eAbilityCasts;
    private final int ultimateCasts;

    public MongoStats(Stats stats) {
        this.score = stats.getScore();
        this.roundsPlayed = stats.getRoundsPlayed();
        this.kills = stats.getKills();
        this.deaths = stats.getDeaths();
        this.assists = stats.getAssists();
        if(stats.getAbilityCasts() != null) {
            this.cAbilityCasts = stats.getAbilityCasts().getGrenadeCasts();
            this.qAbilityCasts = stats.getAbilityCasts().getAbility1Casts();
            this.eAbilityCasts = stats.getAbilityCasts().getAbility2Casts();
            this.ultimateCasts = stats.getAbilityCasts().getUltimateCasts();
        } else {
            this.cAbilityCasts = 0;
            this.qAbilityCasts = 0;
            this.eAbilityCasts = 0;
            this.ultimateCasts = 0;
        }

        this.document = createDocument();
    }

    public MongoStats(Document document) {
        this.score = document.getInteger(SCORE_FIELD);
        this.roundsPlayed = document.getInteger(ROUNDS_PLAYED_FIELD);
        this.kills = document.getInteger(KILLS_FIELD);
        this.deaths = document.getInteger(DEATHS_FIELD);
        this.assists = document.getInteger(ASSISTS_FIELD);
        this.cAbilityCasts = document.getInteger(C_ABILITY_CASTS_FIELD);
        this.qAbilityCasts = document.getInteger(Q_ABILITY_CASTS);
        this.eAbilityCasts = document.getInteger(E_ABILITY_CASTS);
        this.ultimateCasts = document.getInteger(ULTIMATE_CASTS);

        this.document = document;
    }

    public Document toDocument() {
        return document;
    }

    private Document createDocument() {
        return new Document()
                .append(SCORE_FIELD, score)
                .append(ROUNDS_PLAYED_FIELD, roundsPlayed)
                .append(KILLS_FIELD, kills)
                .append(DEATHS_FIELD, deaths)
                .append(ASSISTS_FIELD, assists)
                .append(C_ABILITY_CASTS_FIELD, cAbilityCasts)
                .append(Q_ABILITY_CASTS, qAbilityCasts)
                .append(E_ABILITY_CASTS, eAbilityCasts)
                .append(ULTIMATE_CASTS, ultimateCasts);
    }

}