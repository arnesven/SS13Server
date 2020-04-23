package model.map.doors;

import graphics.sprites.Sprite;
import model.GameData;
import model.events.animation.AnimatedSprite;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class OpenFireDoorAnimationEvent extends DoorAnimationEvent {

    public OpenFireDoorAnimationEvent(GameData gameData, Room r, Door door) {
        super(gameData, r, door, makeAnimatedSprite(door));
    }

    private static Sprite makeAnimatedSprite(Door door) {
        return door.getFireDoorOpenAnimatedSprite();
    }
}
