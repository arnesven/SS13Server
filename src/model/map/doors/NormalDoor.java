package model.map.doors;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.roomactions.LockDoorAction;
import model.items.general.GameItem;
import model.items.general.KeyCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NormalDoor extends Door {

     private static final Sprite NORMAL_DOOR = new Sprite("normaldoor", "doors.png", 11, 17, null);


    public NormalDoor(double x, double y, int fromID, int toID) {
        super(x, y, "Normal", fromID, toID);
    }


    @Override
    protected Sprite getSprite() {
        return NORMAL_DOOR;
    }

    public List<Action> getDoorActions(GameData gameData, Actor forWhom) {
        List<Action> at = new ArrayList<>();
        if (GameItem.hasAnItemOfClass(forWhom, KeyCard.class)) {
            at.add(new LockDoorAction(this));
        }
        return at;
    }
}
