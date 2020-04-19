package model.events.animation;

import model.GameData;
import model.events.Event;
import model.items.general.Grenade;
import model.map.rooms.Room;

public class ExplodingAnimation extends AnimationEvent {
    public ExplodingAnimation(GameData gameData, Room room) {
        super(gameData, room,
                new AnimatedSprite("explosion", "effects.png",
                        11, 7, 32, 32, null, 13));
    }
}
