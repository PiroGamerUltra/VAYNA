package dev.piste.vayna.mongodb.match;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.officer.OfficerAPI;
import dev.piste.vayna.apis.officer.gson.Map;
import dev.piste.vayna.apis.officer.gson.Queue;
import dev.piste.vayna.apis.riot.gson.match.MatchInfo;
import dev.piste.vayna.translations.LanguageManager;
import org.bson.Document;

import java.io.IOException;
import java.util.Date;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class MongoMatchInfo {

    private static final String REGION_FIELD = "region";
    private static final String MAP_ID_FIELD = "mapId";
    private static final String QUEUE_ID_FIELD = "queueId";
    private static final String SEASON_ID_FIELD = "seasonId";
    private static final String START_DATE_FIELD = "startDate";
    private static final String END_DATE_FIELD = "endDate";
    private static final String GAME_VERSION_FIELD = "gameVersion";

    private final Document document;

    private final String region;
    private String mapId;
    private String queueId;
    private final String seasonId;
    private final String gameVersion;
    private final Date startDate;
    private final Date endDate;

    public MongoMatchInfo(MatchInfo matchInfo) throws IOException, HttpErrorException, InterruptedException {
        OfficerAPI officerAPI = new OfficerAPI();
        this.region = matchInfo.getRegion();
        this.seasonId = matchInfo.getSeasonId();
        this.gameVersion = matchInfo.getGameVersion();
        this.startDate = new Date(matchInfo.getGameStartMillis());
        this.endDate = new Date(matchInfo.getGameStartMillis() + matchInfo.getGameLengthMillis());
        for(Map map : officerAPI.getMaps(LanguageManager.getLanguage().getLanguageCode())) {
            if(map.getMapUrl().equals(matchInfo.getMapId())) {
                this.mapId = map.getUuid();
                break;
            }
        }
        if(matchInfo.getQueueId().equals("")) {
            for(Queue queue : officerAPI.getQueues(LanguageManager.getLanguage().getLanguageCode())) {
                if(queue.getQueueId().equals("custom")) {
                    this.queueId = queue.getUuid();
                    break;
                }
            }
        } else {
            for(Queue queue : officerAPI.getQueues(LanguageManager.getLanguage().getLanguageCode())) {
                if(queue.getQueueId().equals(matchInfo.getQueueId())) {
                    this.queueId = queue.getUuid();
                    break;
                }
            }
        }
        document = createDocument();
    }

    public MongoMatchInfo(Document document) {
        this.region = document.getString(REGION_FIELD);
        this.mapId = document.getString(MAP_ID_FIELD);
        this.queueId = document.getString(QUEUE_ID_FIELD);
        this.seasonId = document.getString(SEASON_ID_FIELD);
        this.startDate = document.getDate(START_DATE_FIELD);
        this.endDate = document.getDate(END_DATE_FIELD);
        this.gameVersion = document.getString(GAME_VERSION_FIELD);
        this.document = document;
    }

    public String getRegion() {
        return region;
    }

    public String getMapId() {
        return mapId;
    }

    public String getQueueId() {
        return queueId;
    }

    public String getSeasonId() {
        return seasonId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public Document toDocument() {
        return document;
    }

    private Document createDocument() {
        Document newDocument = new Document();
        newDocument.put(REGION_FIELD, region);
        newDocument.put(MAP_ID_FIELD, mapId);
        newDocument.put(QUEUE_ID_FIELD, queueId);
        newDocument.put(SEASON_ID_FIELD, seasonId);
        newDocument.put(START_DATE_FIELD, startDate);
        newDocument.put(END_DATE_FIELD, endDate);
        newDocument.put(GAME_VERSION_FIELD, gameVersion);
        return newDocument;
    }

}