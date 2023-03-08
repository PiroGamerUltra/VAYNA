package dev.piste.vayna.apis.entities.riotgames;

import com.google.gson.annotations.SerializedName;
import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.OfficerAPI;
import dev.piste.vayna.apis.entities.officer.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Match {

    @SerializedName("matchInfo")
    private MatchInfo matchInfo;
    @SerializedName("players")
    private List<Player> players;
    @SerializedName("coaches")
    private List<Coach> coaches;

    public static class MatchInfo {

        @SerializedName("matchId")
        private String matchId;
        @SerializedName("mapId")
        private String mapPath;
        @SerializedName("gameLengthMillis")
        private long gameLengthMillis;
        @SerializedName("gameStartTimeMillis")
        private long gameStartTimeMillis;
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

        public Map getMap(String languageCode) throws IOException, HttpErrorException, InterruptedException {
            for(Map map : new OfficerAPI().getMaps(languageCode)) {
                if(map.getPath().equals(mapPath)) {
                    return map;
                }
            }
            return null;
        }

        public Date getGameStartDate() {
            return new Date(gameStartTimeMillis);
        }

        public Date getGameEndDate() {
            return new Date(gameStartTimeMillis + gameLengthMillis);
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

        public Queue getQueue(String languageCode) throws IOException, HttpErrorException, InterruptedException {
            for(Queue queue : new OfficerAPI().getQueues(languageCode)) {
                if(queue.getName().equals(queueName)) {
                    return queue;
                }
            }
            return null;
        }

        public Season getSeason(String languageCode) throws IOException, HttpErrorException, InterruptedException {
            for(Season season : new OfficerAPI().getSeasons(languageCode)) {
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

        public String getPartyId() {
            return partyId;
        }

        public Agent getAgent(String languageCode) throws IOException, HttpErrorException, InterruptedException {
            if(agentId == null) return null;
            return new OfficerAPI().getAgent(agentId, languageCode);
        }

        public Stats getStats() {
            return stats;
        }

        public int getRankId() {
            return rankId;
        }

        public PlayerCard getPlayerCard(String languageCode) throws IOException, HttpErrorException, InterruptedException {
            return new OfficerAPI().getPlayerCard(playerCardId, languageCode);
        }

        public PlayerTitle getPlayerTitle(String languageCode) throws IOException, HttpErrorException, InterruptedException {
            return new OfficerAPI().getPlayerTitle(playerTitleId, languageCode);
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

                public Weapon getWeapon(String languageCode) throws IOException, HttpErrorException, InterruptedException {
                    if(weaponId == null) return null;
                    return new OfficerAPI().getWeapon(weaponId, languageCode);
                }

                public Gear getArmor(String languageCode) throws IOException, HttpErrorException, InterruptedException {
                    if(armorId == null) return null;
                    return new OfficerAPI().getGear(armorId, languageCode);
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