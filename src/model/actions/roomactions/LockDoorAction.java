package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.map.doors.NormalDoor;

import java.util.List;

public class LockDoorAction extends Action {

    public LockDoorAction(NormalDoor normalDoor) {
        super("Lock " + normalDoor.getName(), SensoryLevel.OPERATE_DEVICE);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "locked a door";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        performingClient.addTolastTurnInfo("You locked a door! (But really, you didn't)");
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }
}
