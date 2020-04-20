package model.map.doors;

import graphics.sprites.Sprite;
import model.GameData;
import model.events.animation.AnimatedSprite;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class ShutFireDoorAnimationEvent extends DoorAnimationEvent {

    private static Sprite ani;

    public ShutFireDoorAnimationEvent(GameData gameData, Room r, FireDoor fireDoor) {
        super(gameData, r, fireDoor, makeAnimatedSprite(fireDoor));
    }

    private static Sprite makeAnimatedSprite(FireDoor fireDoor) {
        List<Sprite> sps = new ArrayList<>();
        sps.add(new Sprite("doorblank", "doors.png", 11, 19, null));
        sps.add(fireDoor.getInnerDoor().getSprite());
        String suffix ="";
        int extraFrame = 0;
        if (fireDoor.getInnerDoor() instanceof LockedDoor) {
            suffix = "locked";
            extraFrame = 1;
            sps.add(new AnimatedSprite("shuttingfiredoorlocked", "doors.png",
                    14, 10, 32, 32, null, 7, false));
        } else {
            sps.add(new AnimatedSprite("shuttingfiredoor", "doors.png",
                    12, 6, 32, 32, null, 6, false));
        }
        ani = new AnimatedSprite("shuttingfiredoorani" + suffix, sps, 6 + extraFrame, false);
        return ani;
    }

}
