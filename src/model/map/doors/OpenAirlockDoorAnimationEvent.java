package model.map.doors;

import graphics.sprites.Sprite;
import model.GameData;
import model.events.animation.AnimatedSprite;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class OpenAirlockDoorAnimationEvent extends DoorAnimationEvent {
    public OpenAirlockDoorAnimationEvent(GameData gameData, Room from, AirLockDoor airLockDoor, AnimatedSprite aniSprite) {
        super(gameData, from, airLockDoor, aniSprite);
    }

}
