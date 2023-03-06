package dev.piste.vayna.mongodb.match;

import dev.piste.vayna.apis.riot.gson.match.Player;
import dev.piste.vayna.mongodb.match.player.MongoStats;
import org.bson.Document;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class MongoPlayer {

    private static final String PUUID_FIELD = "puuid";
    private static final String GAME_NAME_FIELD = "gameName";
    private static final String TAG_LINE_FIELD = "tagLine";
    private static final String TEAM_ID_FIELD = "teamId";
    private static final String PARTY_ID_FIELD = "partyId";
    private static final String AGENT_ID_FIELD = "agentId";
    private static final String STATS_FIELD = "stats";
    private static final String RANK_FIELD = "rank";
    private static final String PLAYER_CARD_FIELD = "playerCard";
    private static final String PLAYER_TITLE_FIELD = "playerTitle";
    private static final String IS_OBSERVER_FIELD = "isObserver";

    private final Document document;

    private final String puuid;
    private final String gameName;
    private final String tagLine;
    private final String teamId;
    private final String partyId;
    private final String agentId;
    private final MongoStats stats;
    private final int rank;
    private final String playerCard;
    private final String playerTitle;
    private final boolean isObserver;

    public MongoPlayer(Player player) {
        this.puuid = player.getPuuid();
        this.gameName = player.getGameName();
        this.tagLine = player.getTagLine();
        this.teamId = player.getTeamId();
        this.partyId = player.getPartyId();
        this.agentId = player.getCharacterId();
        this.stats = new MongoStats(player.getStats());
        this.rank = player.getCompetitiveTier();
        this.playerCard = player.getPlayerCard();
        this.playerTitle = player.getPlayerTitle();
        this.isObserver = player.isObserver();

        this.document = createDocument();
    }

    public MongoPlayer(Document document) {
        this.puuid = document.getString(PUUID_FIELD);
        this.gameName = document.getString(GAME_NAME_FIELD);
        this.tagLine = document.getString(TAG_LINE_FIELD);
        this.teamId = document.getString(TEAM_ID_FIELD);
        this.partyId = document.getString(PARTY_ID_FIELD);
        this.agentId = document.getString(AGENT_ID_FIELD);
        this.stats = new MongoStats(document.get(STATS_FIELD, Document.class));
        this.rank = document.getInteger(RANK_FIELD);
        this.playerCard = document.getString(PLAYER_CARD_FIELD);
        this.playerTitle = document.getString(PLAYER_TITLE_FIELD);
        this.isObserver = document.getBoolean(IS_OBSERVER_FIELD);

        this.document = document;
    }

    public Document toDocument() {
        return document;
    }

    private Document createDocument() {
        return new Document()
                .append(PUUID_FIELD, puuid)
                .append(GAME_NAME_FIELD, gameName)
                .append(TAG_LINE_FIELD, tagLine)
                .append(TEAM_ID_FIELD, teamId)
                .append(PARTY_ID_FIELD, partyId)
                .append(AGENT_ID_FIELD, agentId)
                .append(STATS_FIELD, stats.toDocument())
                .append(RANK_FIELD, rank)
                .append(PLAYER_CARD_FIELD, playerCard)
                .append(PLAYER_TITLE_FIELD, playerTitle)
                .append(IS_OBSERVER_FIELD, isObserver);
    }

}