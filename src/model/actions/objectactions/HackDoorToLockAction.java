package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.items.NoSuchThingException;
import model.map.doors.DoorState;
import model.map.doors.ElectricalDoor;
import model.map.doors.NormalDoor;

import java.util.List;

public class HackDoorToLockAction extends HackDoorAction{
    private final ElectricalDoor door;

    public HackDoorToLockAction(ElectricalDoor electricalDoor) {
        this.door = electricalDoor;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        try {
            if (door.getDoorState() instanceof DoorState.Normal && door.getDoorMechanism().permitsLock()) {
                door.lockRooms(gameData.getRoomForId(door.getFromId()), gameData.getRoomForId(door.getToId()));
                performingClient.addTolastTurnInfo("You locked the door.");
            } else {
                performingClient.addTolastTurnInfo("Something is wrong with the door, it won't lock! " + failed(gameData, performingClient));
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }
}
