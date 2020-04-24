package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.items.NoSuchThingException;
import model.map.doors.ElectricalDoor;
import util.Logger;

import java.util.List;

public class HackDoorToPoweredAction extends HackDoorAction {
    private final ElectricalDoor door;

    public HackDoorToPoweredAction(ElectricalDoor electricalDoor) {
        this.door = electricalDoor;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        try {
            Logger.log("Executing hack to powered, power supply is" + door.getDoorMechanism().powerSupplyOK());
            if (door.getDoorMechanism().powerSupplyOK()) {
                door.goPowered(gameData.getRoomForId(door.getFromId()), gameData.getRoomForId(door.getToId()));
                performingClient.addTolastTurnInfo("The door has power again.");
            } else {
                performingClient.addTolastTurnInfo("Something is wrong with the door, it's not getting power. " + Action.FAILED_STRING);
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }
}
