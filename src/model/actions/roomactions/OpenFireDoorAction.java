package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.map.GameMap;
import model.map.doors.Door;
import model.map.doors.FireDoor;
import model.map.doors.NormalDoor;
import model.map.rooms.Room;

import java.util.List;

public class OpenFireDoorAction extends Action {
    private final FireDoor door;

    public OpenFireDoorAction(FireDoor fireDoor) {
        super("Open Fire Door" + fireDoor.getName(), SensoryLevel.PHYSICAL_ACTIVITY);
        this.door = fireDoor;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "opened a fire door";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        try {
            openFireDoor(gameData.getRoomForId(door.getFromId()), gameData.getRoomForId(door.getToId()));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    public void openFireDoor(Room from, Room to) {
        GameMap.joinRooms(to, from);
        unwrapInnerDoor(to, door);
        unwrapInnerDoor(from, door);
    }

    private void unwrapInnerDoor(Room room, Door targetDoor) {
        for (int i = 0; i < room.getDoors().length; ++i) {
            if (room.getDoors()[i] == targetDoor) {
                Door newDoor = door.getInnerDoor();
                room.getDoors()[i] = newDoor;
                return;
            }
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }
}
