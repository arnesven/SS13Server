package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.items.NoSuchThingException;
import model.map.doors.ElectricalDoor;
import model.map.rooms.Room;

import java.util.List;

public class HackDoorUnlockAction extends HackDoorAction {
    private final ElectricalDoor door;

    public HackDoorUnlockAction(ElectricalDoor electricalDoor) {
        this.door = electricalDoor;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        try {
            if (door.getDoorMechanism().getLockCord().getState() != 1 && door.getDoorMechanism().getOpenCord().isOK()) {
                door.unlockRooms(gameData.getRoomForId(door.getFromId()), gameData.getRoomForId(door.getToId()));
                if (performingClient instanceof Player) {
                    Room moveTo = gameData.getRoomForId(door.getToId());
                    if (moveTo == performingClient.getPosition()) {
                        moveTo = gameData.getRoomForId(door.getFromId());
                    }
                    ((Player) performingClient).setNextMove(moveTo.getID());
                }
                performingClient.addTolastTurnInfo("You hacked the locked door and moved through!");
                gameData.getGameMode().getMiscHappenings().add(performingClient.getBaseName() + " successfully hacked a locked door!");
            } else {
                performingClient.addTolastTurnInfo("Something is wrong with the door, it can't be unlocked! " + failed(gameData, performingClient));
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }
}
