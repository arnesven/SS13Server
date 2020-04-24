package model.map.doors;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.roomactions.LockDoorAction;
import model.actions.roomactions.MoveThroughAndCloseFireDoorAction;
import model.actions.roomactions.MoveThroughAndLock;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.KeyCard;
import model.map.GameMap;
import model.map.rooms.Room;
import util.HTMLText;

import java.util.List;

public class NormalDoor extends ElectricalDoor {

    private static final Sprite NORMAL_DOOR = new Sprite("normaldoor", "doors.png", 6, 18, null);
    private static final Sprite LOCKED_DOOR = new Sprite("lockeddoor", "doors.png", 12, 17, null);
    public static final Sprite UNPOWERED_DOOR = new Sprite("unpowereddoor", "doors.png", 11, 17, null);

    public NormalDoor(double x, double y, int fromID, int toID, boolean locked) {
        super(x, y, "Normal", fromID, toID, locked);
    }

    public NormalDoor(double x, double y, int fromID, int toID) {
        this(x, y, fromID, toID, false);
    }


    @Override
    public Sprite getNormalSprite() {
        return NORMAL_DOOR;
    }

    @Override
    public Sprite getUnpoweredSprite() { return UNPOWERED_DOOR; }

    @Override
    public Sprite getErrorSprite() {
        return new Sprite("errornormaldoor", "doors.png", 11, 19, null);
    }

    @Override
    public Sprite getBrokenSprite() {
        return new Sprite("brokennormaldoor", "doors.png", 0, 19, null);
    }

    @Override
    public Sprite getLockedSprite() {
        return LOCKED_DOOR;
    }


}
