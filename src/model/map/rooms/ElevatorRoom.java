package model.map.rooms;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.SensoryLevel;
import model.events.Event;
import model.map.GameMap;
import model.map.floors.FloorSet;
import model.objects.general.ElectricalMachinery;
import model.objects.general.ElevatorPanel;
import sounds.Sound;
import util.HTMLText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by erini02 on 17/12/16.
 */
public class ElevatorRoom extends Room {
    private final int[] coordinates;
    private final String[] floorNames;
    private List<Room> floors;
    private boolean needsPowerToRun = true;
    private int currentFloor = 0;
    private int nextFloor = 1;
    private ElectricalMachinery panel;

    public ElevatorRoom(GameData gameData, int id, String name, String shortName, int[] coordinates, Room[] rooms, String[] strings, double[] doors) {
        super(id, name, coordinates[0], coordinates[1], 1, 1, new int[]{}, doors);
        this.floors = new ArrayList<>();
        this.coordinates = coordinates;
        Collections.addAll(floors, rooms);
        this.floorNames = strings;

        gameData.addEvent(new MoveElevatorBetweenFloorsEvent());
        panel = new ElevatorPanel(this);
        this.addObject(panel);

        GameMap.joinRooms(this, floors.get(0));

    }

    private void increaseFloor(GameData gameData) {
        gameData.getMap().separateRooms(ElevatorRoom.this, getCurrentFloor());
        Room next = getNextFloor();
        ElevatorRoom.this.setCoordinates(coordinates[2*nextFloor], coordinates[2*nextFloor+1], getZ());
        gameData.getMap().joinRooms(ElevatorRoom.this, next);

        currentFloor = nextFloor;
        nextFloor = (nextFloor + 1) % floors.size();
        notifyOccupants(gameData);
        notifyOutsiders(gameData);
    }

    private void notifyOutsiders(GameData gameData) {
        for (Actor a : getCurrentFloor().getActors()) {
            a.addTolastTurnInfo(HTMLText.makeText("blue", "Elevator: ding-dong!"));
            if (a instanceof Player) {
                ((Player) a).getSoundQueue().add(new Sound("http://www.ida.liu.se/~erini02/ss13/elevator-ding.mp3"));
            }
        }
    }

    private void notifyOccupants(GameData gameData) {
        for (Actor a : getActors()) {
            a.addTolastTurnInfo(HTMLText.makeText("blue", "Elevator: now on " + floorNames[currentFloor] + "."));
            if (a instanceof Player) {
                ((Player) a).getSoundQueue().add(new Sound("http://www.ida.liu.se/~erini02/ss13/elevator-ding.mp3"));
            }
        }
    }

    public Room getCurrentFloor() {
        return floors.get(currentFloor);
    }

    public Room getNextFloor() {
        return floors.get(nextFloor);
    }

    public boolean isBroken() {
        return panel.isBroken();
    }

    private boolean hasPower(GameData gameData) {
        return !needsPowerToRun || panel.isPowered();
    }

    public void hold() {
        this.nextFloor = currentFloor;
    }

    @Override
    public FloorSet getFloorSet() {
        return new FloorSet("floorelevator", 11, 15);
    }

    private class MoveElevatorBetweenFloorsEvent extends Event {
        @Override
        public void apply(GameData gameData) {
            if (!ElevatorRoom.this.isBroken() && ElevatorRoom.this.hasPower(gameData)) {
                increaseFloor(gameData);
            }
        }

        @Override
        public String howYouAppear(Actor performingClient) {
            return "";
        }

        @Override
        public SensoryLevel getSense() {
            return SensoryLevel.NO_SENSE;
        }
    }


}
