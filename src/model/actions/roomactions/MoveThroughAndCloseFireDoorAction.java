package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.map.doors.ElectricalDoor;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

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
                performingClient.setCharacter(new CanAlsoMoveToDecorator(performingClient.getCharacter(), target));
                ((Player) performingClient).setNextMove(target.getID());
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        }

        super.execute(gameData, performingClient);
    }

    private class CanAlsoMoveToDecorator extends CharacterDecorator {
        private final Room target;

        public CanAlsoMoveToDecorator(GameCharacter gc, Room target) {
            super(gc, "Can also move to " + target.getName());
            this.target = target;
        }

        @Override
        public List<Room> getExtraMoveToLocations(GameData gameData) {
            List<Room> result = new ArrayList<>();
            result.add(target);
            return result;
        }

        @Override
        public void doAfterMovement(GameData gameData) {
            super.doAfterMovement(gameData);
            getActor().removeInstance((GameCharacter gc) -> gc == this);
        }
    }
}
