package model.map.doors;

import model.GameData;
import model.events.animation.AnimatedSprite;
import model.map.rooms.Room;

public class CloseAirlockDoorAnimationEvent extends DoorAnimationEvent {
    public CloseAirlockDoorAnimationEvent(GameData gameData, Room from, AirLockDoor airLockDoor) {
        super(gameData, from, airLockDoor, new AnimatedSprite("closeingairlockdoor", "doors.png",
                10, 7, 32, 32, null, 6, false));
    }

}
