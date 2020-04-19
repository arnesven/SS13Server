package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.map.doors.FireDoor;
import model.map.GameMap;
import model.map.doors.Door;
import model.map.doors.ElectricalDoor;
import model.map.rooms.Room;

import java.util.List;

public class CloseFireDoorAction extends Action {
    private final ElectricalDoor door;

    public CloseFireDoorAction(ElectricalDoor electricalDoor) {
        super("Close Fire Door " + electricalDoor.getNumber(), SensoryLevel.PHYSICAL_ACTIVITY);
        this.door = electricalDoor;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "closed a fire door";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        try {
            shutFireDoor(gameData.getRoomForId(door.getFromId()), gameData.getRoomForId(door.getToId()));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }

    public void shutFireDoor(Room from, Room to) {
        GameMap.separateRooms(to, from);
        wrapInFireDoor(to, door);
        wrapInFireDoor(from, door);
    }

    private void wrapInFireDoor(Room room, Door targetDoor) {
        for (int i = 0; i < room.getDoors().length; ++i) {
            if (room.getDoors()[i] == targetDoor) {
                Door newDoor = new FireDoor(targetDoor);
                room.getDoors()[i] = newDoor;
                return;
            }
        }
    }
}
