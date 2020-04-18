package model.map.doors;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.roomactions.LockDoorAction;
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

    @Override
    public boolean requiresPower() {
        return true;
    }

    @Override
    public ElectricalMachinery getElectricalLock(GameData gameData) {
        try {
            return new ElectricalLock(gameData);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void unlockRooms(Room from, Room to) {
        GameMap.joinRooms(to, from);
        unlockLockedDoor(to, this);
        unlockLockedDoor(from, this);
    }

    private void unlockLockedDoor(Room room, Door targetDoor) {
        for (int i = 0; i < room.getDoors().length; ++i) {
            if (room.getDoors()[i] == targetDoor) {
                NormalDoor newDoor = new NormalDoor(targetDoor.getX(), targetDoor.getY(), targetDoor.getFromId(), targetDoor.getToId());
                room.getDoors()[i] = newDoor;
                return;
            }
        }
    }

    private class ElectricalLock extends ElectricalMachinery {
        public ElectricalLock(GameData gameData) throws NoSuchThingException {
            super("Lock (" + gameData.getRoomForId(getToId()).getName() + " / " + gameData.getRoomForId(getFromId()).getName() + ")", null);
        }

        @Override
        protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {

        }

        @Override
        public void onPowerOff(GameData gameData) {
            try {
                unlockRooms(gameData.getRoomForId(getFromId()), gameData.getRoomForId(getToId()));
                gameData.findObjectOfType(AIConsole.class).informOnStation("Attention, " + getName() + " unlocked because of power failure!", gameData);
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }

            Logger.log(Logger.INTERESTING, " room unlocked because of power failure!");
        }
    }
}
