package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.QuickAction;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.items.general.UniversalKeyCard;
import model.map.doors.ElectricalDoor;
import model.map.rooms.Room;
import sounds.Sound;

import java.util.ArrayList;
import java.util.List;

public class UnLockDoorAction extends Action implements QuickAction {
    private final ElectricalDoor door;

    public UnLockDoorAction(ElectricalDoor lockedDoor) {
        super("Unlock", SensoryLevel.OPERATE_DEVICE);
        this.door = lockedDoor;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "unlocked a door";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (!door.getDoorMechanism().permitsUnlock()) {
            performingClient.addTolastTurnInfo("Something is wrong with the door. It can't be unlocked! " + failed(gameData, performingClient));
            return;
        }

        boolean isAI = performingClient.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AICharacter);
        if ((UniversalKeyCard.findKeyCard(performingClient) != null && UniversalKeyCard.findKeyCard(performingClient).canOpenDoor(door)) || isAI) {
            performingClient.addTolastTurnInfo("You unlocked the door");
        } else if (!isAI) {
            performingClient.addTolastTurnInfo("What, the key card was gone? " + failed(gameData, performingClient));
            return;
        }
        try {
            door.unlockRooms(gameData.getRoomForId(door.getFromId()), gameData.getRoomForId(door.getToId()));
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

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return new Sound("door-unlock");
    }
}
