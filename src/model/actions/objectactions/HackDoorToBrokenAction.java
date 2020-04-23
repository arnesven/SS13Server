package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.map.doors.ElectricalDoor;

import java.util.List;

public class HackDoorToBrokenAction extends HackDoorAction {
    private final ElectricalDoor electricalDoor;

    public HackDoorToBrokenAction(ElectricalDoor electricalDoor) {
        this.electricalDoor = electricalDoor;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        electricalDoor.getDoorMechanism().setHealth(0.0);
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }
}
