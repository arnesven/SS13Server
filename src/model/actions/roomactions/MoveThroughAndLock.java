package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.items.NoSuchThingException;
import model.map.doors.NormalDoor;
import model.map.rooms.Room;

public class MoveThroughAndLock extends LockDoorAction {
    private final NormalDoor door;

    public MoveThroughAndLock(NormalDoor normalDoor) {
        super(normalDoor);
        setName("Move Through and Lock");
        this.door = normalDoor;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        super.execute(gameData, performingClient);
        if (performingClient instanceof Player) {
            Room target = null;
            try {
                target = gameData.getRoomForId(door.getFromId());
                if (performingClient.getPosition() == target) {
                    target = gameData.getRoomForId(door.getToId());
                    ((Player) performingClient).setNextMove(door.getToId());
                } else {
                    ((Player) performingClient).setNextMove(door.getFromId());
                }
                performingClient.setCharacter(new CanAlsoMoveToForOneTurnDecorator(performingClient.getCharacter(), target));
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        }
    }
}
