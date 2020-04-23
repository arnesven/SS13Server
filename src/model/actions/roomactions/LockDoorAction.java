package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.KeyCard;
import model.map.GameMap;
import model.map.doors.Door;
import model.map.doors.ElectricalDoor;
import model.map.doors.LockedDoor;
import model.map.doors.NormalDoor;
import model.map.rooms.Room;
import util.Logger;

import java.util.List;

public class LockDoorAction extends Action {

    private final NormalDoor door;

    public LockDoorAction(NormalDoor normalDoor) {
        super("Lock", SensoryLevel.OPERATE_DEVICE);
        this.door = normalDoor;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "locked a door";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (GameItem.hasAnItemOfClass(performingClient, KeyCard.class) || performingClient.isAI()) {
            performingClient.addTolastTurnInfo("You locked the door");
        } else if (!performingClient.isAI()) {
            performingClient.addTolastTurnInfo("What, the key card was gone? " + Action.FAILED_STRING);
            return;
        }
        try {
            lockRooms(gameData.getRoomForId(door.getFromId()), gameData.getRoomForId(door.getToId()));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }

    public void lockRooms(Room from, Room to) {
        GameMap.separateRooms(to, from);
        lockUnlockedDoor(to, door);
        lockUnlockedDoor(from, door);
    }




    private void lockUnlockedDoor(Room room, NormalDoor targetDoor) {
        for (int i = 0; i < room.getDoors().length; ++i) {
            if (room.getDoors()[i] == targetDoor) {
                Door newDoor = makeIntoLockedDoor(targetDoor);
                room.getDoors()[i] = newDoor;
                return;
            }
        }
    }

    private Door makeIntoLockedDoor(NormalDoor targetDoor) {
        LockedDoor newDoor = new LockedDoor(targetDoor.getX(), targetDoor.getY(), targetDoor.getFromId(), targetDoor.getToId());
        newDoor.setDoorMechanism(targetDoor.getDoorMechanism());
        newDoor.getDoorMechanism().setName(newDoor.getName());
        return newDoor;
    }
}
