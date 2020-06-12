package model;

import model.map.rooms.Room;
import model.map.rooms.StationRoom;
import sounds.Sound;

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
            if (a instanceof Player) {
                if (a.getPosition() == positions.get(a)) {
                    inform(a, gameData);
                } else if (a.getPosition() != positions.get(a) ){
                    playWalkingSound((Player)a, gameData);
                }
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
        if (beholder instanceof Player && beholder.getPosition() instanceof StationRoom) {
            playLeftWalkingSound(beholder, subject, adjacentRoom);

        }
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

    public Room getLastPosition(Actor a) {
        return positions.get(a);
    }


    private void playWalkingSound(Player a, GameData gameData) {
        if (a.getPosition() instanceof StationRoom) {
            if (a.getPosition().getNeighborList().contains(positions.get(a)) && a.getCharacter().getSoundSet().hasSlowWalkingSound()) {
                a.getSoundQueue().add(a.getCharacter().getSoundSet().getSlowWalkingSound());
            } else if (a.getCharacter().getSoundSet().hasFastWalkingSound()){
                a.getSoundQueue().add(a.getCharacter().getSoundSet().getFastWalkingSound());
            }
        }
    }


    private void playLeftWalkingSound(Actor beholder, Actor subject, Room adjacentRoom) {
            if (adjacentRoom == subject.getPosition() && subject.getCharacter().getSoundSet().hasSlowWalkingSound()) {
                ((Player) beholder).getSoundQueue().add(subject.getCharacter().getSoundSet().getSlowWalkingSound());
            } else if (subject.getCharacter().getSoundSet().hasFastWalkingSound()) {
                ((Player) beholder).getSoundQueue().add(subject.getCharacter().getSoundSet().getFastWalkingSound());
            }
    }

}
