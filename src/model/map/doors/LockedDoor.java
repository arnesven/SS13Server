package model.map.doors;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.roomactions.LockDoorAction;
import model.actions.roomactions.UnLockDoorAction;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.KeyCard;

import java.util.ArrayList;
import java.util.List;

public class LockedDoor extends Door {
    private static final Sprite LOCKED_DOOR = new Sprite("lockeddoor", "doors.png", 12, 17, null);

    public LockedDoor(double x, double y, int fromID, int toID) {
        super(x, y, "Locked", fromID, toID);
    }

    @Override
    protected Sprite getSprite() {
        return LOCKED_DOOR;
    }

    public List<Action> getDoorActions(GameData gameData, Actor forWhom) {
        List<Action> at = new ArrayList<>();
        if (GameItem.hasAnItemOfClass(forWhom, KeyCard.class) ||
                forWhom.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AICharacter)) {
            at.add(new UnLockDoorAction(this));
        }
        return at;
    }
}
