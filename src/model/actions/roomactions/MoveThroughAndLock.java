package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.items.NoSuchThingException;
import model.map.doors.ElectricalDoor;
import model.map.doors.NormalDoor;
import model.map.rooms.Room;

public class MoveThroughAndLock extends LockDoorAction {
    private final ElectricalDoor door;

    public MoveThroughAndLock(ElectricalDoor door) {
        super(door);
        setName("Move Through and Lock");
        this.door = door;
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
