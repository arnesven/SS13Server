package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.map.doors.ElectricalDoor;

import java.util.List;

public class HackDoorToUnpoweredAction extends HackDoorAction {

    private final ElectricalDoor door;

    public HackDoorToUnpoweredAction(ElectricalDoor electricalDoor) {
        this.door = electricalDoor;
    }


    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        try {
            door.goUnpowered(gameData.getRoomForId(door.getFromId()), gameData.getRoomForId(door.getToId()));
            performingClient.addTolastTurnInfo("The door lost power.");
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }
}
