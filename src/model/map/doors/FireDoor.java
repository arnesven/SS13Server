package model.map.doors;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.roomactions.OpenFireDoorAction;

import java.util.List;

public class FireDoor extends Door {
    private final Door innerDoor;

    public FireDoor(Door targetDoor) {
        super(targetDoor.getX(), targetDoor.getY(), "Fire", targetDoor.getFromId(), targetDoor.getToId());
        this.innerDoor = targetDoor;
    }

    @Override
    protected Sprite getSprite() {
        if (innerDoor instanceof LockedDoor) {
            return new Sprite("firedoor", "doors.png", 3, 6, null);
        }
        return new Sprite("firedoor", "doors.png", 2, 6, null);
    }

    @Override
    public List<Action> getDoorActions(GameData gameData, Actor forWhom) {
        List<Action> at = super.getDoorActions(gameData, forWhom);
        at.add(new OpenFireDoorAction(this));
        if (!(innerDoor instanceof LockedDoor)) {
            //at.add(new OpenAndMoveThroughFireDoorAction(this));
        }

        return at;
    }

    public Door getInnerDoor() {
        return innerDoor;
    }
}
