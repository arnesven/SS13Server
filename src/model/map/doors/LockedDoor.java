package model.map.doors;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.roomactions.UnLockAndMoveThroughAction;
import model.actions.roomactions.UnLockDoorAction;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.KeyCard;
import model.map.GameMap;
import model.map.rooms.Room;
import model.objects.consoles.AIConsole;
import model.objects.general.ElectricalMachinery;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

public class LockedDoor extends ElectricalDoor {
    private static final Sprite LOCKED_DOOR = new Sprite("lockeddoor", "doors.png", 12, 17, null);

    public LockedDoor(double x, double y, int fromID, int toID) {
        super(x, y, "Locked", fromID, toID);
    }

    @Override
    protected Sprite getSprite() {
        return LOCKED_DOOR;
    }

    public List<Action> getDoorActions(GameData gameData, Actor forWhom) {
        List<Action> at = super.getDoorActions(gameData, forWhom);
        if (GameItem.hasAnItemOfClass(forWhom, KeyCard.class) || forWhom.isAI()) {
            at.add(new UnLockDoorAction(this));
            at.add(new UnLockAndMoveThroughAction(this));
        }
        return at;
    }


    @Override
    protected void thisJustBroke(GameData gameData) {
        try {
            unlockRooms(gameData.getRoomForId(getFromId()), gameData.getRoomForId(getToId()));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }


    public void unlockRooms(Room from, Room to) {
        GameMap.joinRooms(to, from);
        unlockLockedDoor(to, this);
        unlockLockedDoor(from, this);
    }

    private void unlockLockedDoor(Room room, Door targetDoor) {
        for (int i = 0; i < room.getDoors().length; ++i) {
            if (room.getDoors()[i] == targetDoor) {
                NormalDoor newDoor = makeIntoNormalDoor(targetDoor);
                room.getDoors()[i] = newDoor;
                return;
            }
        }
    }

    private NormalDoor makeIntoNormalDoor(Door targetDoor) {
        NormalDoor d = new NormalDoor(targetDoor.getX(), targetDoor.getY(), targetDoor.getFromId(), targetDoor.getToId());
        d.setDoorMechanism(getDoorMechanism());
        d.getDoorMechanism().setName(d.getName());
        return d;
    }

}
