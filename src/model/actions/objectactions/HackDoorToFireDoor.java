package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.map.doors.ElectricalDoor;

import java.util.List;

public class HackDoorToFireDoor extends HackDoorAction {
    private final ElectricalDoor door;

    public HackDoorToFireDoor(ElectricalDoor electricalDoor) {
        this.door = electricalDoor;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (door.getDoorMechanism().getFireCord().isOK()) {
            door.shutFireDoor(gameData);
            performingClient.addTolastTurnInfo("You closed the fire doors.");
        } else {
            performingClient.addTolastTurnInfo("There is something wrong with the door, the fire door won't close. " + Action.FAILED_STRING);
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }
}
