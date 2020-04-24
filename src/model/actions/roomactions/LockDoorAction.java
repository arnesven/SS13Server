package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.KeyCard;
import model.map.doors.ElectricalDoor;

import java.util.List;

public class LockDoorAction extends Action {

    private final ElectricalDoor door;

    public LockDoorAction(ElectricalDoor door) {
        super("Lock", SensoryLevel.OPERATE_DEVICE);
        this.door = door;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "locked a door";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (!door.getDoorMechanism().permitsLock()) {
            performingClient.addTolastTurnInfo("Something is wrong with the door, it can't be locked! " + Action.FAILED_STRING);
        }

        if (GameItem.hasAnItemOfClass(performingClient, KeyCard.class) || performingClient.isAI()) {
            performingClient.addTolastTurnInfo("You locked the door");
        } else if (!performingClient.isAI()) {
            performingClient.addTolastTurnInfo("What, the key card was gone? " + Action.FAILED_STRING);
            return;
        }
        try {
            door.lockRooms(gameData.getRoomForId(door.getFromId()), gameData.getRoomForId(door.getToId()));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }


}
