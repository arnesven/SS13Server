package model.events.animation;

import model.Actor;
import model.GameData;
import model.events.Event;
import model.items.general.Grenade;
import model.map.rooms.Room;

public class ExplodingAnimation extends AnimationEvent {
    public ExplodingAnimation(GameData gameData, Room room) {
        super(gameData, room,
  //              new AnimatedSprite("explosion", "effects.png",
  //                      11, 7, 32, 32, null, 13, false));
                new AnimatedSprite("explosionbig", "bigexplo.png", 0, 0, 96, 96, null, 8, false));
    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return "Explosion";
    }
}
