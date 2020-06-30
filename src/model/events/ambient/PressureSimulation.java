package model.events.ambient;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.events.Event;
import model.events.RoomPressureEvent;
import model.map.rooms.*;
import util.Logger;

import java.util.*;

public class PressureSimulation extends Event {

    private Map<Room, Double> roomPressures = new HashMap<>();


    @Override
    public String howYouAppear(Actor performingClient) {
        return null;
    }

    @Override
    public SensoryLevel getSense() {
        return null;
    }

    public Double getPressureFor(Room room) {
        return roomPressures.get(room);
    }

    @Override
    public void apply(GameData gameData) {
        for (Room r : getAffectedRooms(gameData)) {
            if (!roomPressures.containsKey(r)) {
                if (r.startsWithPressure()) {
                    roomPressures.put(r, 1.0);
                } else {
                    roomPressures.put(r, 0.0);
                }
                r.addEvent(new RoomPressureEvent(this, r));
            }
        }

        printTable(gameData);
        runManipulators(gameData);
        printTable(gameData);
        adjustForNearbyRooms(gameData);
        printTable(gameData);
        affectActors(gameData);
        removeFires(gameData);
    }


    protected void runManipulators(GameData gameData) {
        for (Room r : getAffectedRooms(gameData)) {

            double currentPressure = roomPressures.get(r);

            for (PressureManipulator ps : getPressureSinksFor(gameData, r)) {
                currentPressure = ps.handlePressure(r, currentPressure);
            }

            if (r.getLifeSupport() != null) {
                currentPressure = r.getLifeSupport().handlePressure(r, currentPressure);
            }

            roomPressures.put(r, currentPressure);
        }
    }


    private void adjustForNearbyRooms(GameData gameData) {
        Map<Room, Double> newPressures = new HashMap<>();
        for (Room r : roomPressures.keySet()) {
            newPressures.put(r, roomPressures.get(r));
        }

        for (Room r : getAffectedRooms(gameData)) {
            List<Room> neighbors = r.getNeighborsEvenThroughDoors(gameData);
            for (Room neigh : neighbors) {
                if (roomPressures.get(r) >= 1.0) {
                    if (roomPressures.get(neigh) < 0.9 && neigh.sucksPressureFromNeighbors(gameData)) {
                        newPressures.put(neigh, newPressures.get(neigh) * 1.05);
                        newPressures.put(r, newPressures.get(r) * 0.95);
                    }
                }
            }
        }
        roomPressures = newPressures;
    }


    protected Collection<PressureManipulator> getPressureSinksFor(GameData gameData, Room r) {
        List<PressureManipulator> result = new ArrayList<>();
        if (r.hasHullBreach()) {
            result.add(HullBreach.findHullBreachIn(r));
        }

        if (r instanceof PressureManipulator) {
            result.add((PressureManipulator)r);
        }

        for (Room neigh : r.getNeighborList()) {
            if (neigh instanceof PressureManipulator && r.sucksPressureFromNeighbors(gameData)) {
                result.add((PressureManipulator) neigh);
            }
        }
        return result;
    }

    protected Collection<Room> getAffectedRooms(GameData gameData) {
        return gameData.getAllRooms();
    }


    private void printTable(GameData gameData) {
        Logger.log("==== Pressure Table ===");
        for (Room r : roomPressures.keySet()) {
            Logger.log(String.format("%-20s%5.2f", r.getName(), roomPressures.get(r)));
        }
    }



    private void affectActors(GameData gameData) {
        for (Room r : getAffectedRooms(gameData)) {
            for (Target t : r.getTargets(gameData)) {
                RoomPressureEvent.exposeTargetToPressure(getPressureFor(r), t, gameData);
            }
        }
    }


    private void removeFires(GameData gameData) {
        for (Room r : getAffectedRooms(gameData)) {
            RoomPressureEvent.removeFireIfApplicable(r, getPressureFor(r));
        }
    }



}
