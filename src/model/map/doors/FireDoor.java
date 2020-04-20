package model.map.doors;

import graphics.ClientInfo;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.roomactions.OpenFireDoorAction;
import model.events.animation.AnimatedSprite;
import model.events.animation.AnimationEvent;
import model.items.NoSuchThingException;
import model.map.GameMap;
import model.map.rooms.Room;

import java.util.List;

public class FireDoor extends Door {
    private final Door innerDoor;

    public FireDoor(Door targetDoor) {
        super(targetDoor.getX(), targetDoor.getY(), "Fire", targetDoor.getFromId(), targetDoor.getToId());
        this.innerDoor = targetDoor;
    }

    @Override
    protected Sprite getSprite() {
        if (isAnimating()) {
            return Sprite.blankSprite();
        }
        if (innerDoor instanceof LockedDoor) {
            return new Sprite("firedoorlocked", "doors.png", 3, 6, null);
        }
        return new Sprite("firedoor", "doors.png", 6, 6, null);
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



    public void openFireDoor(GameData gameData, Actor performingClient) {
        try {
            Room from = gameData.getRoomForId(getFromId());
            Room to = gameData.getRoomForId(getToId());
            Door theDoor = openFireDoor(from, to);
            from.addEvent(new OpenFireDoorAnimationEvent(gameData, from, this));
            to.addEvent(new OpenFireDoorAnimationEvent(gameData, to, this));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    public Door openFireDoor(Room from, Room to) {
        GameMap.joinRooms(to, from);
        Door d = unwrapInnerDoor(to, this);
        if (d == null) {
            d = unwrapInnerDoor(from, this);
        }
        return d;
    }

    private Door unwrapInnerDoor(Room room, Door targetDoor) {
        for (int i = 0; i < room.getDoors().length; ++i) {
            if (room.getDoors()[i] == targetDoor) {
                Door newDoor = this.getInnerDoor();
                room.getDoors()[i] = newDoor;
                return newDoor;
            }
        }
        return null;
    }
}
