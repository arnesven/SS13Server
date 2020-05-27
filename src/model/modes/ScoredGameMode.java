package model.modes;

import model.Actor;
import model.GameData;
import model.Player;
import model.events.ambient.OngoingEvent;
import model.events.ambient.SimulatePower;
import model.items.CosmicArtifact;
import model.items.NoSuchThingException;
import model.items.general.Bible;
import model.items.general.GameItem;
import model.map.rooms.ExoticPlanet;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.PirateNPC;
import model.npcs.animals.CatNPC;
import model.npcs.animals.ChimpNPC;
import model.npcs.behaviors.CrazyBehavior;
import model.npcs.robots.TARSNPC;
import model.objects.Altar;
import model.objects.consoles.CrimeRecordsConsole;
import model.objects.consoles.MarketConsole;
import model.objects.decorations.BurnMark;
import model.objects.general.BloodyMess;
import model.objects.general.ElectricalMachinery;
import model.objects.general.GameObject;
import model.objects.general.MailBox;
import model.objects.mining.GeneralManufacturer;
import util.Logger;
import util.Pair;

import java.util.List;
import java.util.Map;

public abstract class ScoredGameMode extends GameMode {

    private static final int EVENT_FIX_POINTS = 20;
    private static final int POINTS_FOR_BROKEN_OBJECTS = 25;
    private static final int POINTS_FROM_DEAD_PIRATES = 50;
    private static final int POINTS_FROM_BOMBS_DEFUSED = 50;
    private static final int BAD_SECURITY = 10;


    protected abstract int getModeSpecificPoints(GameData gameData);

    public GameOver getGameResult(GameData gameData) {
        if (gameData.isAllDead()) {
            return GameOver.ALL_DEAD;
        } else if (gameData.getRound() == gameData.getNoOfRounds()) {
            return GameOver.TIME_IS_UP;
        }
        return null;
    }

    public int getScore(GameData gameData) {
        int result = 0;

        result += getModeSpecificPoints(gameData);
        result += pointsFromBrokenObjects(gameData);
        result += pointsFromFires(gameData);
        result += pointsFromBreaches(gameData);
        result += pointsFromDirtyStation(gameData);
        result += pointsFromParasites(gameData);
        result += pointsFromCat(gameData);
        result += pointsFromTARS(gameData);
        result += pointsFromChimp(gameData);
        result += pointsFromPower(gameData);
        result += pointsFromGod(gameData);
        result += pointsFromPirates(gameData);
        result += pointsFromBombsDefused(gameData);
        result += pointsFromSecurity(gameData);
        result += pointsFromMining(gameData);
        result += pointsFromSelling(gameData);
        result += pointsFromExploredPlanets(gameData);
        result += cosmicArtifactFound(gameData);
        return result;
    }

    public int pointsFromSelling(GameData gameData) {
        try {
            MarketConsole console = gameData.findObjectOfType(MarketConsole.class);
            return (int)(console.getTotalSellValue() / 20.0);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int pointsFromDirtyStation(GameData gameData) {
        int total = 0;

        for (Room r : gameData.getMap().getStationRooms()) {
            for (GameObject ob : r.getObjects()) {
                if (ob instanceof BloodyMess || ob instanceof BurnMark) {
                    total -= 10;
                }
            }
        }

        return total;
    }

    public int pointsFromExploredPlanets(GameData gameData) {
        int total = 0;
        for (Room r : gameData.getAllRooms()) {
            if (r instanceof ExoticPlanet) {
                total += ((ExoticPlanet)r).isExplored()?50:0;

            }
        }
        return total;
    }

    public int pointsFromMining(GameData gameData) {
        int total = 0;
        for (GameObject obj : gameData.getAllObjects()) {
            if (obj instanceof GeneralManufacturer) {
                total += ((GeneralManufacturer)obj).getTotalCharged();
            }
        }
        return total;
    }

    public int pointsFromSecurity(GameData gameData) {
        try {
            int sum = 0;
            CrimeRecordsConsole crc = gameData.findObjectOfType(CrimeRecordsConsole.class);
            for (Map.Entry<Actor, List<Pair<String, Actor>>> criminalCrimeReporterEntry : crc.getReportsHistory().entrySet()) {
                Actor criminal = criminalCrimeReporterEntry.getKey();
                for (Pair<String, Actor> crimeReporterPair : criminalCrimeReporterEntry.getValue()) {
                    Actor reporter = crimeReporterPair.second;
                    if (reporter instanceof Player && !isAntagonist((Player) reporter)) {
                        if ((criminal instanceof Player && !isAntagonist((Player) criminal)) || isABadPersonNPC(criminal)) {
                            sum -= BAD_SECURITY * crc.getTimeForCrime(crimeReporterPair.first);
                        }
                    }
                }

            }
            return sum;

        } catch (NoSuchThingException e) {
            Logger.log(Logger.CRITICAL, "Could not find Crime Records Console");
            return 0;
        }


    }

    private boolean isABadPersonNPC(Actor criminal) {
        if (criminal instanceof NPC) {
            NPC crimNPC = (NPC)criminal;
            if (crimNPC.isInfected()) {
                return true;
            }

            if (crimNPC.getActionBehavior() instanceof CrazyBehavior) {
                return true;
            }
        }
        return false;
    }

    public int pointsFromBombsDefused(GameData gameData) {
        return POINTS_FROM_BOMBS_DEFUSED * gameData.getGameMode().getBombsDefused();
    }

    public int pointsFromPirates(GameData gameData) {
        int res = 0;
        for (Actor a : gameData.getActors()) {
            if (a instanceof PirateNPC && a.isDead()) {
                res += POINTS_FROM_DEAD_PIRATES;
            }
        }
        return res;
    }


    public int pointsFromGod(GameData gameData) {
        Bible b = getBible(gameData);
        int biblePoints = 0;
        if (b == null) {
            Logger.log("Bible not found");
        } else {
            biblePoints = b.getGodPoints();
        }

        int mailboxpoints = 0;
        try {
            MailBox box = gameData.findObjectOfType(MailBox.class);
            mailboxpoints = box.getPoints();
        } catch (NoSuchThingException e) {
            Logger.log("Mailbox not found");
        }

        int altarpoints = 0;
        try {
            Altar altar = gameData.findObjectOfType(Altar.class);
            altarpoints = altar.getPoints();
        } catch (NoSuchThingException nste) {
            Logger.log("No altar found");
        }

        return biblePoints + mailboxpoints + altarpoints;
    }

    private Bible getBible(GameData gameData) {
        for (Actor a : gameData.getActors()) {
            try {
                Bible b = (Bible)GameItem.getItem(a, new Bible());
                return b;
            } catch (NoSuchThingException nse) {
//				Logger.log(Logger.CRITICAL, "Bible not found!");
            }

        }

        for (Room r : gameData.getNonHiddenStationRooms()) {
            for (GameItem it : r.getItems()) {
                if (it instanceof Bible) {
                    //Logger.log("Found bible in room " + r.getName());
                    return (Bible)it;
                }
            }
        }
        return null;
    }

    public int pointsFromPower(GameData gameData) {
        SimulatePower sp = (SimulatePower)gameData.getGameMode().getEvents().get("simulate power");
        if ((sp.getAvailablePower(gameData)/sp.getPowerDemand(gameData)) > 0.95) {
            return 200;
        }

        return 0;
    }

    public int pointsFromBrokenObjects(GameData gameData) {
        int result = 0;
        for (Room r : gameData.getNonHiddenStationRooms()) {
            for (GameObject obj : r.getObjects()) {
                if (obj instanceof ElectricalMachinery) {
                    if (((ElectricalMachinery)obj).isBroken()) {
                        result -= POINTS_FOR_BROKEN_OBJECTS;
                    }
                }
            }
        }
        return result;
    }

    public int pointsFromParasites(GameData gameData) {
        int res = 0;
        for (NPC npc : allParasites) {
            if (npc.isDead()) {
                res += 5;
            }
        }

        return res;
    }

    public int pointsFromCat(GameData gameData) {
        for (NPC npc : gameData.getNPCs()) {
            if (npc instanceof CatNPC) {
                if (npc.isDead()) {
                    return -50;
                }
            }
        }
        return 0;
    }

    public int pointsFromBreaches(GameData gameData) {
        OngoingEvent breaches = (OngoingEvent) this.getEvents().get("hull breaches");
        return (breaches.noOfFixed() - breaches.noOfOngoing()) * EVENT_FIX_POINTS;
    }

    public int pointsFromFires(GameData gameData) {
        OngoingEvent fire = (OngoingEvent) this.getEvents().get("fires");
        return (fire.noOfFixed() - fire.noOfOngoing()) * EVENT_FIX_POINTS;
    }

    public int pointsFromTARS(GameData gameData) {
        for (NPC npc : gameData.getNPCs()) {
            if (npc instanceof TARSNPC) {
                if (npc.isDead()) {
                    return -200;
                }
            }
        }

        return 0;
    }

    protected int pointsFromChimp(GameData gameData) {
        for (NPC npc : gameData.getNPCs()) {
            if (npc instanceof ChimpNPC) {
                if (npc.isDead()) {
                    return -50;
                }
            }
        }
        return 0;
    }


    public int cosmicArtifactFound(GameData gameData) {
        for (Player pl : gameData.getPlayersAsList()) {
            for (GameItem it : pl.getItems()) {
                if (it instanceof CosmicArtifact) {
                    if (!isAntagonist(pl)) {
                        return 500;
                    } else {
                        return 0;
                    }
                }
            }
        }
        return 0;
    }

}
