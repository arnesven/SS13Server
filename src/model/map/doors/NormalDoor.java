package model.map.doors;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.roomactions.LockDoorAction;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.KeyCard;
import model.map.GameMap;
import model.map.rooms.Room;
import util.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NormalDoor extends ElectricalDoor {

     private static final Sprite NORMAL_DOOR = new Sprite("normaldoor", "doors.png", 11, 17, null);


    public NormalDoor(double x, double y, int fromID, int toID) {
        super(x, y, "Normal", fromID, toID);
    }


    @Override
    protected Sprite getSprite() {
        if (isAnimating()) {
            return Sprite.blankSprite();
        }
        if (isBroken()) {
            return new Sprite("brokennormaldoor", "doors.png", 0, 19, null);
        }
        return NORMAL_DOOR;
    }

    public List<Action> getDoorActions(GameData gameData, Actor forWhom) {
        List<Action> at = super.getDoorActions(gameData, forWhom);
        if (!isBroken() && (GameItem.hasAnItemOfClass(forWhom, KeyCard.class) ||
                forWhom.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AICharacter))) {
            at.add(new LockDoorAction(this));
        }
        return at;
    }


}
