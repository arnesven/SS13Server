package model.actions.roomactions;

import model.GameData;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

class CanAlsoMoveToForOneTurnDecorator extends CharacterDecorator {
    private final Room target;

    public CanAlsoMoveToForOneTurnDecorator(GameCharacter gc, Room target) {
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
