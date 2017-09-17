package model.objects.consoles;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.rooms.Room;
import model.objects.general.PowerConsumer;
import model.objects.general.BreakableObject;
import model.objects.general.ElectricalMachinery;
import model.objects.general.GameObject;
import model.objects.general.Repairable;
import util.Logger;

import java.io.Serializable;
import java.util.*;

/**
 * Created by erini02 on 26/11/16.
 */
public abstract class PowerSource extends BreakableObject implements Repairable {


    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {

    }


    protected abstract List<Room> getAffectedRooms(GameData gameData);




    private interface PowerUpdater extends Serializable {
        double update(double currentPower, double lsPer, double liPer, double eqPer,
                      List<? extends PowerConsumer> ls,
                      List<? extends PowerConsumer> lights,
                      List<? extends PowerConsumer> eq);
    }

    public double startingPower;
    private static final double FIXED_INCREASE = 6.75;
    private static final double ONGOING_INCREASE = 3.6;

    private static final double EQUIPMENT_POWER_USE_PCT = 0.60;
    private static final double LIFE_SUPPORT_POWER_USE_PCT = 0.30;
    private static final double LIGHTING_POWER_USE_PCT = 0.10;

    private Map<String, PowerUpdater> updaters;
    private double level;
    private double dlevel = 0.0;
    private List<String> prios;
    private List<Room> noLifeSupport = new ArrayList<>();
    private List<Room> noLight = new ArrayList<>();
    private List<ElectricalMachinery> noPower = new ArrayList<>();
    private List<Double> history = new ArrayList<>();
    private int lightBefore;
    private int lsBefore;
    private int eqBefore;

    public double getStartingPower() {
        return startingPower;
    }

    public PowerSource(double normal_output, Room r, GameData gameData) {
        super("Power Source", 5, r);
        this.startingPower = normal_output;
        level = startingPower;
        setupPrio();
        setupUpdaters();

    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (getPowerOutput() < 0.01) {
            return new Sprite("powersourcenothing", "stationobjs.png", 150);
        } else if (getPowerOutput() < 0.25) {
            return new Sprite("powersourcelowest", "stationobjs.png", 156);
        } else if (getPowerOutput() < 0.5) {
            return new Sprite("powersourcelower", "stationobjs.png", 157);
        } else if (getPowerOutput() < 0.75) {
            return new Sprite("powersourcelow", "stationobjs.png", 159);
        } else if (getPowerOutput() < 1.1) {
            return new Sprite("powersourcenormal", "stationobjs.png", 161);
        } else if (getPowerOutput() < 1.5) {
            return new Sprite("powersourcehigh", "stationobjs.png", 167);
        } else if (getPowerOutput() < 1.5) {
            return new Sprite("powersourcehigher", "stationobjs.png", 178);
        }
        return new Sprite("powersource", "stationobjs.png", 160);
    }

    private double internalUpdater(double cp, double pp, List<? extends PowerConsumer> list) {
        while (cp - pp > 0 && list.size() > 0) {
            PowerConsumer pc = list.remove(0);
            cp -= pp * pc.getPowerConsumptionFactor();
        }
        return cp;
    }

    public double getLevel() {
        if (isBroken()) {
            return 0.0;
        }
        return level;
    }

    private void setupUpdaters() {
        updaters = new HashMap<>();
        updaters.put("Life Support", (double currentPower, double lsPer,
                                 double liPer, double eqPer, List<? extends PowerConsumer> ls,
                                 List<? extends PowerConsumer> lights, List<? extends  PowerConsumer> eq) ->
                internalUpdater(currentPower, lsPer, ls));

        updaters.put("Lighting", (double currentPower, double lsPer,
                                 double liPer, double eqPer, List<? extends PowerConsumer> ls,
                                 List<? extends PowerConsumer> lights, List<? extends  PowerConsumer> eq) ->
                internalUpdater(currentPower, liPer, lights));

        updaters.put("Equipment",(double currentPower, double lsPer,
                                 double liPer, double eqPer, List<? extends PowerConsumer> ls,
                                 List<? extends PowerConsumer> lights, List<? extends  PowerConsumer> eq) ->
                internalUpdater(currentPower, eqPer, eq));
    }

    private void setupPrio() {
        this.prios = new ArrayList<>();
        prios.add("Life Support");
        prios.add("Lighting");
        prios.add("Equipment");
    }

    public void setLevel(boolean fixed, boolean increase) {
        if (fixed) {
            level = Math.max(0.0, getLevel() + FIXED_INCREASE * (increase?+1.0:-1.0));
            dlevel = 0.0;
        } else {
            dlevel = ONGOING_INCREASE * (increase?+1.0:-1.0);
        }
        Logger.log("Powerlevel is now " + getLevel());
    }

    public List<String> getPrios() {
        return prios;
    }

    public void setHighestPrio(String selected) {
        Iterator<String> it = prios.iterator();
        while(it.hasNext()) {
            if (it.next().equals(selected)) {
                it.remove();
            }
        }
        prios.add(0, selected);
        Logger.log("Prios are now: " + prios);
    }

    public double getPowerLevel() {
        return level;
    }

    public void updateYourself(GameData gameData) {
        history.add(getLevel());
        level = Math.max(0.0, getLevel()+dlevel);
        Logger.log("Current power: " + getLevel());

        noLifeSupport = new ArrayList<Room>();
        noLight       = new ArrayList<Room>();
        List<ElectricalMachinery> oldNoPower = noPower;
        noPower       = new ArrayList<ElectricalMachinery>();


        List<Room> allRooms = new ArrayList<>();
        allRooms.addAll(getAffectedRooms(gameData));

        double noOfRooms = allRooms.size();
        double lsPowerPerRoom    = startingPower * LIFE_SUPPORT_POWER_USE_PCT / noOfRooms;
        double lightPowerPerRoom = startingPower * LIGHTING_POWER_USE_PCT / noOfRooms;

        for (Room room : getAffectedRooms(gameData)) {
            for (GameObject obj : room.getObjects()) {
                if (obj instanceof ElectricalMachinery && !(obj instanceof GeneratorConsole)) {
                    noPower.add((ElectricalMachinery) obj);
                }
            }
        }
        Collections.shuffle(noPower);

        double eqPowerPer = startingPower * EQUIPMENT_POWER_USE_PCT / (double)(noPower.size());
        Logger.log(" POWER: Priority is " + prios);
        Logger.log(" POWER: Life support (MW per room): " + lsPowerPerRoom);
        Logger.log(" POWER: Lighting (MW per room)    : " + lightPowerPerRoom);
        Logger.log(" POWER: Equipment (MW per machine): " + eqPowerPer);

        noLight.addAll(getAffectedRooms(gameData));
        Collections.shuffle(noLight);
        noLifeSupport.addAll(getAffectedRooms(gameData));
        Collections.shuffle(noLifeSupport);

        Collections.sort(noPower);

        lightBefore = noLight.size();
        lsBefore = noLifeSupport.size();
        eqBefore = noPower.size();

        double currentPower = this.getLevel() + startingPower *0.01;
        for (String type : prios) {
            currentPower = updaters.get(type).update(currentPower,
                    lsPowerPerRoom, lightPowerPerRoom, eqPowerPer,
                    noLifeSupport, noLight, noPower);
        }

        Logger.log(getLSString());
        for (Room r : noLifeSupport) {
            Logger.log("     " + r.getName());
        }

        Logger.log(getLightString());

        Logger.log(getEquipmentString());
        runPowerOnOrOffFunctions(gameData, oldNoPower);
    }


    private void runPowerOnOrOffFunctions(GameData gameData,
                                          List<ElectricalMachinery> oldNoPower) {
        for (Room r : getAffectedRooms(gameData)) {
            for (GameObject obj : r.getObjects()) {
                if (obj instanceof ElectricalMachinery) {
                    ElectricalMachinery em = (ElectricalMachinery) obj;
                    if (oldNoPower.contains(em) && !getNoPowerObjects().contains(em)) {
                        em.onPowerOn(gameData);
                    } else if (!oldNoPower.contains(em) && getNoPowerObjects().contains(em)) {
                        em.onPowerOff(gameData);
                    }
                }
            }
        }
    }

    public List<Room> getNoLifeSupportRooms() {
        return noLifeSupport;
    }

    public double getPowerOutput() {
        return (getLevel()/ startingPower);
    }

    public List<ElectricalMachinery> getNoPowerObjects() {
        return noPower;
    }

    public List<Room> getNoLightRooms() {
        return noLight;
    }

    public void addToLevel(double d) {
        level = Math.max(0.0, getLevel() + d);
    }

    public String getLSString() {
        return "Life support units (" + (lsBefore - noLifeSupport.size()) + "/" + lsBefore + ")";
    }

    public String getLightString() {
        return "Lights (" + (lightBefore - noLight.size()) + "/" + lightBefore + ")";
    }

    public String getEquipmentString() {
        return "Electrical equipment (" + (eqBefore - noPower.size()) + "/" + eqBefore + ")";
    }

    public List<Double> getHistory() {
        return history;
    }

    @Override
    public void doWhenRepaired(GameData gameData) {

    }
}
