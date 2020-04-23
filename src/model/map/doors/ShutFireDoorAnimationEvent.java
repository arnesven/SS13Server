package model.map.doors;

import graphics.sprites.Sprite;
import model.GameData;
import model.events.animation.AnimatedSprite;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class ShutFireDoorAnimationEvent extends DoorAnimationEvent {

    public ShutFireDoorAnimationEvent(GameData gameData, Room r, FireDoor fireDoor) {
        super(gameData, r, fireDoor, makeAnimatedSprite(fireDoor));
    }

    private static Sprite makeAnimatedSprite(FireDoor fireDoor) {
        return fireDoor.getAnimatedSprite();

    }

}
