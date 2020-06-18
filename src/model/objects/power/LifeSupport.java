package model.objects.power;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.events.Event;
import model.events.ambient.ColdEvent;
import model.events.ambient.PressureManipulator;
import model.map.rooms.Room;
import model.objects.general.ElectricalMachinery;
import util.Logger;
import util.MyRandom;

import java.util.ArrayList;

public class LifeSupport extends ElectricalMachinery implements PressureManipulator {
    private static final double SLOW_PRESSURE_DECREASE = 0.10;
    private final Room room;
    private int lifeSupportLastOnIn;
    private ColdEvent coldEvent;
    private double lastLoss;

    public LifeSupport(Room r) {
        super(r.getName() + " LS", r);
        this.room = r;
        setPowerPriority(1);
        lastLoss = 0.0;
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {

    }

    @Override
    public double getPowerConsumption() {
        return 0.002;   // 2 kW
    }

    @Override
    public String getTypeName() {
        return "Life Support";
    }

    @Override
    public void onPowerOff(GameData gameData) {
        lifeSupportLastOnIn = gameData.getRound();
    }

    @Override
    public void onPowerOn(GameData gameData) {
        lifeSupportLastOnIn = gameData.getRound() + 1;
    }

    public int getLifeSupportLastOnIn() {
        return lifeSupportLastOnIn;
    }

    public void addColdEvent(GameData gameData) {
        for (Event e : room.getEvents()) {
            if (e instanceof ColdEvent) {
                return;  // already cold, don't need to add another one.
            }
        }
        this.coldEvent = new ColdEvent(room);
        room.addEvent(coldEvent);
        gameData.addEvent(coldEvent);
        coldEvent.apply(gameData);
    }

    public void removeColdEvent(GameData gameData) {
        if (room.getEvents().contains(coldEvent)) {
            room.removeEvent(coldEvent);
            gameData.removeEvent(coldEvent);
        }
    }

    @Override
    public double handlePressure(Room r, double currentPressure) {
        double newIncrease = getLastLoss() * (0.45 + (MyRandom.nextDouble() * 0.20));
        String message = "Life support in " + r.getName() + ", current pressure is " + currentPressure + ", last loss was " + getLastLoss() + ", will do an increase of " + newIncrease;
        setLastLoss(1.0 - currentPressure);
        if (isPowered()) {
            if (currentPressure < 1.0) {
                Logger.log(message);
                return currentPressure + newIncrease;
            }
            if (currentPressure > 1.5) {
                return currentPressure - SLOW_PRESSURE_DECREASE;
            }
            //Pressure OK for now...
        }

        return currentPressure;
    }

    private void setLastLoss(double v) {
        this.lastLoss = v;
    }

    private double getLastLoss() {
        return lastLoss;
    }
}
