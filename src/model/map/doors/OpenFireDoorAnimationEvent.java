package model.map.doors;

import graphics.sprites.Sprite;
import model.GameData;
import model.events.Event;
import model.events.animation.AnimatedSprite;
import model.events.animation.AnimationEvent;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class OpenFireDoorAnimationEvent extends FireDoorAnimationEvent {

    private static Sprite ani;

    public OpenFireDoorAnimationEvent(GameData gameData, Room r, Door door) {
        super(gameData, r, door, makeAnimatedSprite(door));
    }

    private static Sprite makeAnimatedSprite(Door door) {
        List<Sprite> sps = new ArrayList<>();
        sps.add(new Sprite("doorblank", "doors.png", 11, 19, null));
        sps.add(door.getSprite());
        sps.add(new AnimatedSprite("openingfiredoor", "doors.png",
                14, 9, 32, 32, null, 7, false));
        ani = new AnimatedSprite("openingfiredoorani", sps, 7, false);
        return ani;
    }
}
