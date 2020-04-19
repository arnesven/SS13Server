package model.map.doors;

import graphics.ClientInfo;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.events.animation.AnimatedSprite;
import model.events.animation.AnimationEvent;
import model.map.rooms.Room;

public class ShutFireDoorAnimationEvent extends FireDoorAnimationEvent {

    private static final Sprite ani =  new AnimatedSprite("shuttingfiredoor", "doors.png",
            0, 9, 32, 32, null, 7, false);

    public ShutFireDoorAnimationEvent(GameData gameData, Room r, FireDoor fireDoor) {
        super(gameData, r, fireDoor, ani);
    }

}
