package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.items.NoSuchThingException;
import model.map.doors.ElectricalDoor;
import model.map.rooms.Room;

public class MoveThroughAndCloseFireDoorAction extends CloseFireDoorAction {
    private final ElectricalDoor door;

    public MoveThroughAndCloseFireDoorAction(ElectricalDoor electricalDoor) {
        super(electricalDoor);
        setName("Move Through and " + super.getName());
        this.door = electricalDoor;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (performingClient instanceof Player) {
            try {
                Room target = gameData.getRoomForId(door.getToId());
                if (target == performingClient.getPosition()) {
                    target = gameData.getRoomForId(door.getFromId());
                }
                performingClient.setCharacter(new CanAlsoMoveToForOneTurnDecorator(performingClient.getCharacter(), target));
                ((Player) performingClient).setNextMove(target.getID());
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        }

        super.execute(gameData, performingClient);
    }

}
