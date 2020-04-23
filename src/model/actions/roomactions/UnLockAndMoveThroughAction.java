package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.doors.LockedDoor;

public class UnLockAndMoveThroughAction extends UnLockDoorAction {
    private final LockedDoor door;

    public UnLockAndMoveThroughAction(LockedDoor lockedDoor) {
        super(lockedDoor);
        setName("Unlock and Move Through");
        this.door = lockedDoor;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        super.execute(gameData, performingClient);
        if (performingClient instanceof Player) {
            if (door.getToId() == performingClient.getPosition().getID()) {
                ((Player) performingClient).setNextMove(door.getFromId());
            } else {
                ((Player) performingClient).setNextMove(door.getToId());
            }
        }
    }
}
