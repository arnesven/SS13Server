package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.QuickAction;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.general.UniversalKeyCard;
import model.map.doors.ElectricalDoor;

import java.util.ArrayList;
import java.util.List;

public class LockDoorAction extends Action implements QuickAction {

    private final ElectricalDoor door;

    public LockDoorAction(ElectricalDoor door) {
        super("Lock", SensoryLevel.OPERATE_DEVICE);
        this.door = door;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "locked a door";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (!door.getDoorMechanism().permitsLock()) {
            performingClient.addTolastTurnInfo("Something is wrong with the door, it can't be locked! " + failed(gameData, performingClient));
            return;
        }

        if ((UniversalKeyCard.findKeyCard(performingClient) != null && UniversalKeyCard.findKeyCard(performingClient).canOpenDoor(door)) || performingClient.isAI()) {
            performingClient.addTolastTurnInfo("You locked the door");
        } else if (!performingClient.isAI()) {
            performingClient.addTolastTurnInfo("What, the key card was gone? " + failed(gameData, performingClient));
            return;
        }
        try {
            door.lockRooms(gameData.getRoomForId(door.getFromId()), gameData.getRoomForId(door.getToId()));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }


    @Override
    public void performQuickAction(GameData gameData, Player performer) {
        execute(gameData, performer);
    }

    @Override
    public boolean isValidToExecute(GameData gameData, Player performer) {
        return true;
    }

    @Override
    public List<Player> getPlayersWhoNeedToBeUpdated(GameData gameData, Player performer) {
        List<Player> result = new ArrayList<>();
        result.addAll(gameData.getPlayersAsList());
        return result;
    }
}
