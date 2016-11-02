package model;

import model.map.Room;
import model.npcs.behaviors.PathFinding;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by erini02 on 03/09/16.
 */
public class MovementData {
    private Map<Actor, Room> positions = new HashMap<>();

    public MovementData(GameData gameData) {
        for (Actor a : gameData.getActors()) {
            positions.put(a, a.getPosition());
        }
    }

    public void informPlayersOfMovements(GameData gameData) {
        for (Actor a : gameData.getActors()) {
            // a is a player and has not moved this round
            if (a instanceof Player && a.getPosition() == positions.get(a)) {
                inform(a, gameData);
            }
        }
    }

    private void inform(Actor beholder, GameData gameData) {
        for (Actor subject : gameData.getActors()) {
            // they were in same room
            if (positions.get(subject) == beholder.getPosition()) {
                // ... but aren't any more
                if (subject.getPosition() != beholder.getPosition() &&
                        beholder.getCharacter().isVisible()) {
                    try {
                        heLeftTowards(beholder, subject, findAdjacentRoom(beholder.getPosition(), subject.getPosition()));
                    } catch (RoomNotFoundException e) {
                        wereDidHeGo(beholder, subject);
                    }
                }
            }
        }
    }

    private void wereDidHeGo(Actor beholder, Actor subject) {
        // do nothing
    }

    private void heLeftTowards(Actor beholder, Actor subject, Room adjacentRoom) {
        beholder.addTolastTurnInfo(subject.getPublicName() + " left towards " + adjacentRoom.getName() + ".");
    }

    private Room findAdjacentRoom(Room a, Room b) throws RoomNotFoundException {
        if (a.getNeighborList().contains(b)) {
            return b;
        }
        for (Room c : b.getNeighborList()) {
            if (a.getNeighborList().contains(c)) {
                return c;
            }
        }
        throw new RoomNotFoundException();
    }
}
