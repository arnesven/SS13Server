package model.objects.power;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.events.Event;
import model.events.ambient.ColdEvent;
import model.map.rooms.Room;
import model.objects.general.ElectricalMachinery;

import java.util.ArrayList;

public class LifeSupport extends ElectricalMachinery {
    private final Room room;
    private int lifeSupportLastOnIn;
    private ColdEvent coldEvent;

    public LifeSupport(Room r) {
        super("Life Support in " + r.getName(), r);
        this.room = r;
        setPowerPriority(1);
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
}
