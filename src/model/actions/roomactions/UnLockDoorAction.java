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
import model.map.doors.LockedDoor;
import model.map.doors.NormalDoor;
import model.map.rooms.Room;

import java.util.List;

public class UnLockDoorAction extends Action {
    private final Door door;

    public UnLockDoorAction(LockedDoor lockedDoor) {
        super("Unlock " + lockedDoor.getName(), SensoryLevel.OPERATE_DEVICE);
        this.door = lockedDoor;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "unlocked a door";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        boolean isAI = performingClient.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AICharacter);
        if (GameItem.hasAnItemOfClass(performingClient, KeyCard.class) || isAI) {
            performingClient.addTolastTurnInfo("You unlocked the door");
        } else if (!isAI) {
            performingClient.addTolastTurnInfo("What, the key card was gone? " + Action.FAILED_STRING);
            return;
        }
        try {
            unlockRooms(gameData.getRoomForId(door.getFromId()), gameData.getRoomForId(door.getToId()));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }

    public void unlockRooms(Room from, Room to) {
        GameMap.joinRooms(to, from);
        unlockLockedDoor(to, door);
        unlockLockedDoor(from, door);
    }

    private void unlockLockedDoor(Room room, Door targetDoor) {
        for (int i = 0; i < room.getDoors().length; ++i) {
            if (room.getDoors()[i] == targetDoor) {
                NormalDoor newDoor = new NormalDoor(targetDoor.getX(), targetDoor.getY(), targetDoor.getFromId(), targetDoor.getToId());
                room.getDoors()[i] = newDoor;
                return;
            }
        }
    }

}
