package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.map.doors.ElectricalDoor;

import java.util.List;

public class ShowDoorHackingFancyFrameAction extends Action {
    private final ElectricalDoor door;

    public ShowDoorHackingFancyFrameAction(GameData gameData, Actor forWhom, ElectricalDoor electricalDoor) {
        super("Examine Door Mechanism", SensoryLevel.OPERATE_DEVICE);
        this.door = electricalDoor;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "fiddled with the door mechanism";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {

    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }
}
