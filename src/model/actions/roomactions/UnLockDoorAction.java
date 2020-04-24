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
    private final LockedDoor door;

    public UnLockDoorAction(LockedDoor lockedDoor) {
        super("Unlock", SensoryLevel.OPERATE_DEVICE);
        this.door = lockedDoor;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "unlocked a door";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (!door.getDoorMechanism().permitsUnlock()) {
            performingClient.addTolastTurnInfo("Something is wrong with the door. It can't be unlocked! " + Action.FAILED_STRING);
        }

        boolean isAI = performingClient.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AICharacter);
        if (GameItem.hasAnItemOfClass(performingClient, KeyCard.class) || isAI) {
            performingClient.addTolastTurnInfo("You unlocked the door");
        } else if (!isAI) {
            performingClient.addTolastTurnInfo("What, the key card was gone? " + Action.FAILED_STRING);
            return;
        }
        try {
            door.unlockRooms(gameData.getRoomForId(door.getFromId()), gameData.getRoomForId(door.getToId()));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }



}
