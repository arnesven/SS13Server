package model.characters.decorators;

import graphics.sprites.Sprite;
import model.GameData;
import model.characters.general.GameCharacter;
import model.events.animation.AnimatedSprite;

public class StunningSparksAnimationDecorator extends OneTurnAnimationDecorator {
    public StunningSparksAnimationDecorator(GameCharacter character, GameData gameData) {
        super(character, "stunsparks", gameData);
    }

    @Override
    protected Sprite getAnimatedSprite() {
        return new AnimatedSprite("stunsparks", "effects3.png",
                14, 20, 32, 32, getActor(), 12, true);
    }
}
