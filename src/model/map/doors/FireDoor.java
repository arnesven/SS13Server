package model.map.doors;

import graphics.ClientInfo;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.roomactions.OpenAndMoveThroughFireDoorAction;
import model.actions.roomactions.OpenFireDoorAction;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.events.animation.AnimatedSprite;
import model.events.animation.AnimationEvent;
import model.items.NoSuchThingException;
import model.map.GameMap;
import model.map.rooms.Room;

import java.util.ArrayList;
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
        if (!(innerDoor instanceof LockedDoor) && !forWhom.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AICharacter)) {
            at.add(new OpenAndMoveThroughFireDoorAction(this));
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
            Door theDoor = openFireDoor(from, to, innerDoor instanceof LockedDoor);
            from.addEvent(new OpenFireDoorAnimationEvent(gameData, from, this));
            to.addEvent(new OpenFireDoorAnimationEvent(gameData, to, this));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    public Door openFireDoor(Room from, Room to, boolean wasLocked) {
        if (!wasLocked) {
            GameMap.joinRooms(to, from);
        }
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


    public Sprite getOpenFireDoorAnimationSprite() {
        List<Sprite> sps = new ArrayList<>();
        sps.add(new Sprite("doorblank", "doors.png", 11, 19, null));
        sps.add(getInnerDoor().getSprite());
        String suffix ="";
        int extraFrame = 0;
        if (getInnerDoor() instanceof LockedDoor) {
            suffix = "locked";
            extraFrame = 1;
            sps.add(new AnimatedSprite("shuttingfiredoorlocked", "doors.png",
                    14, 10, 32, 32, null, 7, false));
        } else {
            sps.add(new AnimatedSprite("shuttingfiredoor", "doors.png",
                    12, 6, 32, 32, null, 6, false));
        }
        Sprite ani = new AnimatedSprite("shuttingfiredoorani" + suffix, sps, 6 + extraFrame, false);
        return ani;
    }
}
