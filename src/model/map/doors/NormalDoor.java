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
        } else if (getDoorMechanism() != null && getDoorMechanism().hasError()) {
            return new Sprite("errornormaldoor", "doors.png", 11, 19, null);
        }
        return NORMAL_DOOR;
    }

    public List<Action> getDoorActions(GameData gameData, Actor forWhom) {
        List<Action> at = super.getDoorActions(gameData, forWhom);
        if (getDoorMechanism().permitsLock() && !isBroken() && (GameItem.hasAnItemOfClass(forWhom, KeyCard.class) || forWhom.isAI())) {
            at.add(new LockDoorAction(this));
            at.add(new MoveThroughAndLock(this));
        }
        if (getDoorMechanism().getFireCord().isOK() || !forWhom.isAI()) {
            at.add(new MoveThroughAndCloseFireDoorAction(this));
        }
        return at;
    }

    @Override
    public String getDiodeColor() {
        if (isBroken()) {
            return HTMLText.makeText("white", "gray","_DARK");
        } else if (getDoorMechanism().hasError()) {
            return HTMLText.makeText("black", "yellow", "YELLOW");
        }
        return HTMLText.makeText("black", "#0bf900","GREEN");
    }

    public void lockRooms(Room from, Room to) {
        GameMap.separateRooms(to, from);
        lockUnlockedDoor(to);
        lockUnlockedDoor(from);
    }




    private void lockUnlockedDoor(Room room) {
        for (int i = 0; i < room.getDoors().length; ++i) {
            if (room.getDoors()[i] == this) {
                Door newDoor = makeIntoLockedDoor();
                room.getDoors()[i] = newDoor;
                return;
            }
        }
    }

    private Door makeIntoLockedDoor() {
        LockedDoor newDoor = new LockedDoor(getX(), getY(), getFromId(), getToId());
        newDoor.setDoorMechanism(getDoorMechanism());
        newDoor.getDoorMechanism().setName(newDoor.getName());
        newDoor.getDoorMechanism().getLockCord().setState(1);
        return newDoor;
    }





}
