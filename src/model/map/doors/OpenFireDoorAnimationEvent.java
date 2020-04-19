package model.map.doors;

import graphics.sprites.Sprite;
import model.GameData;
import model.events.Event;
import model.events.animation.AnimatedSprite;
import model.events.animation.AnimationEvent;
import model.map.rooms.Room;

public class OpenFireDoorAnimationEvent extends FireDoorAnimationEvent {

    private static final Sprite ani =  new AnimatedSprite("openingfiredoor", "doors.png",
            6, 9, 32,32, null, 8, false);

    public OpenFireDoorAnimationEvent(GameData gameData, Room r, Door door) {
        super(gameData, r, door, ani);
        // TODO: Make this animated sprite by combining fire door animation and door sprite underneath!
    }
}
