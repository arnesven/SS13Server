package model.map.doors;

import model.GameData;
import model.events.animation.AnimatedSprite;
import model.map.rooms.Room;

public class CloseAirlockDoorAnimationEvent extends DoorAnimationEvent {
    public CloseAirlockDoorAnimationEvent(GameData gameData, Room from, AirLockDoor airLockDoor, AnimatedSprite aniSprite) {
        super(gameData, from, airLockDoor, aniSprite);
    }

}
