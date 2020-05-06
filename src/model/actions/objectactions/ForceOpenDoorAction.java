package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.Tools;
import model.items.tools.Wrench;
import model.items.weapons.Crowbar;
import model.map.doors.ElectricalDoor;

import java.util.List;

public class ForceOpenDoorAction extends Action {
    private final ElectricalDoor door;

    public ForceOpenDoorAction(ElectricalDoor unpoweredDoor) {
        super("Force Open", SensoryLevel.PHYSICAL_ACTIVITY);
        this.door = unpoweredDoor;
    }

    public static boolean hasAnApplicableItem(Actor performingClient) {
        return GameItem.hasAnItemOfClass(performingClient, Tools.class) ||
                GameItem.hasAnItemOfClass(performingClient, Crowbar.class) ||
                GameItem.hasAnItemOfClass(performingClient, Wrench.class);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "forced the door open";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        try {
            if (hasAnApplicableItem(performingClient)) {
                door.crowbarOpen(gameData.getRoomForId(door.getFromId()), gameData.getRoomForId(door.getToId()));
            } else {
                performingClient.addTolastTurnInfo("What, the item was missing? " + Action.FAILED_STRING);
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }
}
