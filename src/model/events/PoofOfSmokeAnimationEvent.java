package model.events;

import graphics.sprites.EmptySpriteObject;
import graphics.sprites.Sprite;
import model.GameData;
import model.events.animation.AnimatedSprite;
import model.events.animation.AnimationEvent;
import model.map.rooms.Room;

public class PoofOfSmokeAnimationEvent extends AnimationEvent {
    public PoofOfSmokeAnimationEvent(GameData gameData, Room r) {
        super(gameData, r,
                new AnimatedSprite("poofofsmokeani", "wizardstuff.png", 0, 3, 32, 32,
                        new EmptySpriteObject("Wizard"),
                        8, false), "Wizard");
    }
}
