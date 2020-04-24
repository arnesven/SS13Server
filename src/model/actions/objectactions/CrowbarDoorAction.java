package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.Tools;
import model.map.doors.ElectricalDoor;

import java.util.List;

public class CrowbarDoorAction extends Action {
    private final ElectricalDoor door;

    public CrowbarDoorAction(ElectricalDoor unpoweredDoor) {
        super("Crowbar Open", SensoryLevel.PHYSICAL_ACTIVITY);
        this.door = unpoweredDoor;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "crowbared the door open";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        try {
            if (GameItem.hasAnItemOfClass(performingClient, Tools.class)) {
                door.crowbarOpen(gameData.getRoomForId(door.getFromId()), gameData.getRoomForId(door.getToId()));
            } else {
                performingClient.addTolastTurnInfo("What, the crowbar is missing? " + Action.FAILED_STRING);
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }
}
