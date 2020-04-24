package model.map.doors;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.objectactions.CrowbarDoorAction;
import model.actions.objectactions.CrowbarDoorAndMoveThroughAction;
import model.items.general.GameItem;
import model.items.general.Tools;
import model.map.GameMap;
import model.map.rooms.Room;
import util.HTMLText;

import java.util.List;

public class UnpoweredDoor extends ElectricalDoor {

    public static final Sprite UNPOWERED_DOOR = new Sprite("unpowereddoor", "doors.png", 11, 17, null);
    private final boolean wasLocked;

    public UnpoweredDoor(double x, double y, int fromID, int toID, boolean wasLocked) {
        super(x, y, "Unpowered", fromID, toID);
        this.wasLocked = wasLocked;
    }

    protected boolean wasLocked() {
        return wasLocked;
    }

    @Override
    protected Sprite getSprite() {
        return UNPOWERED_DOOR;
    }

    public List<Action> getDoorActions(GameData gameData, Actor forWhom) {
        List<Action> at = super.getDoorActions(gameData, forWhom);
        if (GameItem.hasAnItemOfClass(forWhom, Tools.class)) {
            at.add(new CrowbarDoorAction(this));
            at.add(new CrowbarDoorAndMoveThroughAction(this));
        }
        return at;
    }

    @Override
    protected UnpoweredDoor getUnpoweredCounterpart() {
        return new UnpoweredDoor(getX(), getY(), getFromId(), getToId(), wasLocked);
    }

    @Override
    public String getDiodeColor() {
        return HTMLText.makeText("white", "gray", "_DARK");
    }



    public void crowbarOpen(Room from, Room to) {
        GameMap.joinRooms(to, from);
        makeNormalAndBroken(to);
        makeNormalAndBroken(from);
    }

    private void makeNormalAndBroken(Room room) {
        for (int i = 0; i < room.getDoors().length; ++i) {
            if (room.getDoors()[i] == this) {
                NormalDoor newDoor = makeIntoNormalBrokenDoor();
                room.getDoors()[i] = newDoor;
                return;
            }
        }
    }

    private NormalDoor makeIntoNormalBrokenDoor() {
        NormalDoor d = getBrokenCounterpart();
        d.setDoorMechanism(getDoorMechanism());
        d.getDoorMechanism().setName(d.getName());
        d.getDoorMechanism().setHealth(0.0);
        return d;
    }

    protected NormalDoor getBrokenCounterpart() {
        return new NormalDoor(getX(), getY(), getFromId(), getToId());
    }

    @Override
    protected ElectricalDoor getPoweredCounterpart() {
        if (wasLocked) {
            return new LockedDoor(getX(), getY(), getFromId(), getToId());
        }
        return getBrokenCounterpart();
    }

    public void goPowered(Room from, Room to) {
        if (!wasLocked) {
            GameMap.joinRooms(to, from);
        }
        powerDoor(to);
        powerDoor(from);
    }

    private void powerDoor(Room room) {
        for (int i = 0; i < room.getDoors().length; ++i) {
            if (room.getDoors()[i] == this) {
                Door newDoor = makeIntoPowered();
                room.getDoors()[i] = newDoor;
                return;
            }
        }
    }

    protected Door makeIntoPowered() {
        ElectricalDoor d = getPoweredCounterpart();
        d.setDoorMechanism(getDoorMechanism());
        d.getDoorMechanism().setName(d.getName());
        return d;
    }


}
