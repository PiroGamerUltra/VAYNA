package dev.piste.vayna.http.models.riotgames;

import com.google.gson.annotations.SerializedName;
import dev.piste.vayna.http.HttpErrorException;
import dev.piste.vayna.http.apis.OfficerAPI;
import dev.piste.vayna.http.models.officer.*;
import dev.piste.vayna.translations.Language;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Match {

    @SerializedName("_id")
    private String id;
    @SerializedName("matchInfo")
    private MatchInfo matchInfo;
    @SerializedName("players")
    private List<Player> players;
    @SerializedName("coaches")
    private List<Coach> coaches;
    @SerializedName("teams")
    private List<Team> teams;
    @SerializedName("roundResults")
    private List<RoundResult> rounds;

    public MatchInfo getMatchInfo() {
        return matchInfo;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Coach> getCoaches() {
        return coaches;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public List<RoundResult> getRounds() {
        return rounds;
    }

    public Match setMongoId() {
        id = matchInfo.getMatchId();
        return this;
    }

    public static class MatchInfo {

        @SerializedName("matchId")
        private String matchId;
        @SerializedName("mapId")
        private String mapPath;
        @SerializedName("gameLengthMillis")
        private long gameLengthMillis;
        @SerializedName("gameStartMillis")
        private long gameStartMillis;
        @SerializedName("provisioningFlowId")
        private String provisioningFlowId;
        @SerializedName("isCompleted")
        private boolean isCompleted;
        @SerializedName("customGameName")
        private String customGameName;
        @SerializedName("queueId")
        private String queueName;
        @SerializedName("gameMode")
        private String gameModePath;
        @SerializedName("isRanked")
        private boolean isRanked;
        @SerializedName("seasonId")
        private String seasonId;

        public String getMatchId() {
            return matchId;
        }

        public String getMapPath() {
            return mapPath;
        }

        public Map getMap(Language language) throws IOException, HttpErrorException, InterruptedException {
            for(Map map : new OfficerAPI().getMaps(language)) {
                if(map.getPath().equals(mapPath)) {
                    return map;
                }
            }
            return null;
        }

        public Date getGameStartDate() {
            return new Date(gameStartMillis);
        }

        public Date getGameEndDate() {
            return new Date(gameStartMillis + gameLengthMillis);
        }

        public String getProvisioningFlowId() {
            return provisioningFlowId;
        }

        public boolean isCompleted() {
            return isCompleted;
        }

        public String getCustomGameName() {
            return customGameName;
        }

        public String getQueueName() {
            if(queueName.equals("")) return "custom";
            return queueName;
        }

        public Queue getQueue(Language language) throws IOException, HttpErrorException, InterruptedException {
            if(queueName.equals("")) return new OfficerAPI().getQueue("63d60a3e-4838-695d-9077-e9af5ed523ca", language);
            for(Queue queue : new OfficerAPI().getQueues(language)) {
                if(queue.getName().equals(queueName)) {
                    return queue;
                }
            }
            return null;
        }

        public Season getSeason(Language language) throws IOException, HttpErrorException, InterruptedException {
            for(Season season : new OfficerAPI().getSeasons(language)) {
                if(season.getId().equals(seasonId)) {
                    return season;
                }
            }
            return null;
        }

    }

    public static class Player {

        @SerializedName("puuid")
        private String puuid;
        @SerializedName("gameName")
        private String name;
        @SerializedName("tagLine")
        private String tag;
        @SerializedName("teamId")
        private String teamId;
        @SerializedName("partyId")
        private String partyId;
        @SerializedName("characterId")
        private String agentId;
        @SerializedName("stats")
        private Stats stats;
        @SerializedName("competitiveTier")
        private int rankId;
        @SerializedName("playerCard")
        private String playerCardId;
        @SerializedName("playerTitle")
        private String playerTitleId;

        public String getPUUID() {
            return puuid;
        }

        public String getName() {
            return name;
        }

        public String getTag() {
            return tag;
        }

        public String getRiotId() {
            return name + "#" + tag;
        }

        public String getTeamId() {
            return teamId;
        }

        public String getPartyId() {
            return partyId;
        }

        public String getAgentId() {
            return agentId;
        }

        public Agent getAgent(Language language) throws IOException, HttpErrorException, InterruptedException {
            if(agentId == null) return null;
            return new OfficerAPI().getAgent(agentId, language);
        }

        public Stats getStats() {
            return stats;
        }

        public int getRankId() {
            return rankId;
        }

        public PlayerCard getPlayerCard(Language language) throws IOException, HttpErrorException, InterruptedException {
            return new OfficerAPI().getPlayerCard(playerCardId, language);
        }

        public PlayerTitle getPlayerTitle(Language language) throws IOException, HttpErrorException, InterruptedException {
            return new OfficerAPI().getPlayerTitle(playerTitleId, language);
        }

        public static class Stats {

            @SerializedName("score")
            private int combatScore;
            @SerializedName("roundsPlayed")
            private int playedRoundsCount;
            @SerializedName("kills")
            private int killCount;
            @SerializedName("deaths")
            private int deathCount;
            @SerializedName("assists")
            private int assistCount;
            @SerializedName("playTimeMillis")
            private long playTimeMillis;
            @SerializedName("abilityCasts")
            private AbilityCastCounts abilityCastCounts;

            public int getCombatScore() {
                return combatScore;
            }

            public int getPlayedRoundsCount() {
                return playedRoundsCount;
            }

            public int getKillCount() {
                return killCount;
            }

            public int getDeathCount() {
                return deathCount;
            }

            public int getAssistCount() {
                return assistCount;
            }

            public AbilityCastCounts getAbilityCastCounts() {
                return abilityCastCounts;
            }

            public static class AbilityCastCounts {

                @SerializedName("grenadeCasts")
                private int c;
                @SerializedName("ability1Casts")
                private int q;
                @SerializedName("ability2Casts")
                private int e;
                @SerializedName("ultimateCasts")
                private int x;

                public int getC() {
                    return c;
                }

                public int getQ() {
                    return q;
                }

                public int getE() {
                    return e;
                }

                public int getX() {
                    return x;
                }

            }

        }

    }

    public static class Coach {

        @SerializedName("puuid")
        private String puuid;
        @SerializedName("teamId")
        private String teamId;

        public String getPUUID() {
            return puuid;
        }

        public String getTeamId() {
            return teamId;
        }

    }

    public static class Team {

        @SerializedName("teamId")
        private String id;
        @SerializedName("won")
        private boolean isWinner;
        @SerializedName("roundsPlayed")
        private int playedRoundsCount;
        @SerializedName("roundsWon")
        private int wonRoundsCount;
        @SerializedName("numPoints")
        private int points;

        public String getId() {
            return id;
        }

        public boolean isWinner() {
            return isWinner;
        }

        public int getPlayedRoundsCount() {
            return playedRoundsCount;
        }

        public int getWonRoundsCount() {
            return wonRoundsCount;
        }

        public int getPoints() {
            return points;
        }

    }

    public static class RoundResult {

        @SerializedName("roundNum")
        private int number;
        @SerializedName("roundResult")
        private String result;
        @SerializedName("roundCeremony")
        private String ceremony;
        @SerializedName("winningTeam")
        private String winningTeamId;
        @SerializedName("bombPlanter")
        private String bombPlanterPuuid;
        @SerializedName("bombDefuser")
        private String bombDefuserPuuid;
        @SerializedName("plantRoundTime")
        private int plantTime;
        @SerializedName("plantPlayerLocations")
        private List<PlayerLocation> plantPlayerLocations;
        @SerializedName("plantLocation")
        private Location plantLocation;
        @SerializedName("plantSite")
        private String plantSite;
        @SerializedName("defuseRoundTime")
        private int defuseTime;
        @SerializedName("defusePlayerLocations")
        private List<PlayerLocation> defusePlayerLocations;
        @SerializedName("defuseLocation")
        private Location defuseLocation;
        @SerializedName("playerStats")
        private List<PlayerRoundStats> playerStats;
        @SerializedName("roundResultCode")
        private String resultCode;

        public static class PlayerLocation {

            @SerializedName("puuid")
            private String puuid;
            @SerializedName("viewRadians")
            private double viewRadians;
            @SerializedName("location")
            private Location location;

            public String getPUUID() {
                return puuid;
            }

            public double getViewRadians() {
                return viewRadians;
            }

            public Location getLocation() {
                return location;
            }

        }

        public static class Location {

            @SerializedName("x")
            private int x;
            @SerializedName("y")
            private int y;

            public int getX() {
                return x;
            }

            public int getY() {
                return y;
            }

        }

        public static class PlayerRoundStats {

            @SerializedName("puuid")
            private String puuid;
            @SerializedName("kills")
            private List<Kill> kills;
            @SerializedName("damage")
            private List<Damage> damages;
            @SerializedName("score")
            private int combatScore;
            @SerializedName("economy")
            private Economy economy;
            @SerializedName("ability")
            private AbilityEffects abilityEffects;

            public static class Kill {

                @SerializedName("timeSinceGameStartMillis")
                private long timeSinceGameStartMillis;
                @SerializedName("timeSinceRoundStartMillis")
                private long timeSinceRoundStartMillis;
                @SerializedName("killer")
                private String killerPuuid;
                @SerializedName("victim")
                private String victimPuuid;
                @SerializedName("victimLocation")
                private Location victimLocation;
                @SerializedName("assistants")
                private List<String> assistantPuuids;
                @SerializedName("playerLocations")
                private List<PlayerLocation> playerLocations;
                @SerializedName("finishingDamage")
                private FinishingDamage finishingDamage;

                public long getTimeSinceGameStartMillis() {
                    return timeSinceGameStartMillis;
                }

                public long getTimeSinceRoundStartMillis() {
                    return timeSinceRoundStartMillis;
                }

                public String getKillerPUUID() {
                    return killerPuuid;
                }

                public String getVictimPUUID() {
                    return victimPuuid;
                }

                public Location getVictimLocation() {
                    return victimLocation;
                }

                public List<String> getAssistantPuuids() {
                    return assistantPuuids;
                }

                public List<PlayerLocation> getPlayerLocations() {
                    return playerLocations;
                }

                public FinishingDamage getFinishingDamage() {
                    return finishingDamage;
                }

                public static class FinishingDamage {

                    @SerializedName("damageType")
                    private String damageType;
                    @SerializedName("damageItem")
                    private String damageItemId;
                    @SerializedName("isSecondaryFireMode")
                    private boolean isSecondaryFireMode;

                    public String getDamageType() {
                        return damageType;
                    }

                    public String getDamageItemId() {
                        return damageItemId;
                    }

                    public boolean isSecondaryFireMode() {
                        return isSecondaryFireMode;
                    }

                }

            }

            public static class Damage {

                @SerializedName("receiver")
                private String receiverPuuid;
                @SerializedName("damage")
                private int damageCount;
                @SerializedName("legshots")
                private int legshotCount;
                @SerializedName("bodyshots")
                private int bodyshotCount;
                @SerializedName("headshots")
                private int headshotCount;

                public String getReceiverPUUID() {
                    return receiverPuuid;
                }

                public int getDamageCount() {
                    return damageCount;
                }

                public int getLegshotCount() {
                    return legshotCount;
                }

                public int getBodyshotCount() {
                    return bodyshotCount;
                }

                public int getHeadshotCount() {
                    return headshotCount;
                }

            }

            public static class Economy {

                @SerializedName("loadoutValue")
                private int loadoutValue;
                @SerializedName("weapon")
                private String weaponId;
                @SerializedName("armor")
                private String armorId;
                @SerializedName("remaining")
                private int remainingMoney;
                @SerializedName("spent")
                private int spentMoney;

                public int getLoadoutValue() {
                    return loadoutValue;
                }

                public Weapon getWeapon(Language language) throws IOException, HttpErrorException, InterruptedException {
                    if(weaponId == null) return null;
                    return new OfficerAPI().getWeapon(weaponId, language);
                }

                public Gear getArmor(Language language) throws IOException, HttpErrorException, InterruptedException {
                    if(armorId == null) return null;
                    return new OfficerAPI().getGear(armorId, language);
                }

                public int getRemainingMoney() {
                    return remainingMoney;
                }

                public int getSpentMoney() {
                    return spentMoney;
                }

            }

            public static class AbilityEffects {

                @SerializedName("grenadeEffects")
                private String c;
                @SerializedName("ability1Effects")
                private String q;
                @SerializedName("ability2Effects")
                private String e;
                @SerializedName("ultimateEffects")
                private String x;

                public String getC() {
                    return c;
                }

                public String getQ() {
                    return q;
                }

                public String getE() {
                    return e;
                }

                public String getX() {
                    return x;
                }

            }

        }

    }

}