package model.events.animation;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.map.rooms.Room;

public class BigExplosionAnimation extends AnimationEvent {

    public static Sprite explosion =  new AnimatedSprite("explosionbig", "bigexplo.png", 0, 0, 96, 96, null, 16, false);

    public BigExplosionAnimation(GameData gameData, Room room) {
        super(gameData, room, explosion, "Explosion");
    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return "Explosion";
    }
}
