package model.events.animation;

import model.Actor;
import model.GameData;
import model.events.Event;
import model.items.general.Grenade;
import model.map.rooms.Room;

public class BigExplosionAnimation extends AnimationEvent {
    public BigExplosionAnimation(GameData gameData, Room room) {
        super(gameData, room,
                new AnimatedSprite("explosionbig", "bigexplo.png", 0, 0, 96, 96, null, 16, false));
    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return "Explosion";
    }
}
