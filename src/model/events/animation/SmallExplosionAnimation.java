package model.events.animation;

import model.GameData;
import model.events.Event;
import model.map.rooms.Room;

public class SmallExplosionAnimation extends AnimationEvent {
    public SmallExplosionAnimation(GameData gameData, Room room) {
        super(gameData, room,   new AnimatedSprite("explosion", "smallexplo.png",
                0, 0, 32, 32, null, 14, false), "Explosion");
    }
}
