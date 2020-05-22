package model.characters.decorators;

import graphics.sprites.Sprite;
import model.GameData;
import model.characters.general.GameCharacter;
import model.events.animation.AnimatedSprite;

public class PoofOfSmokeAnimationDecorator extends OneTurnAnimationDecorator {
    public PoofOfSmokeAnimationDecorator(GameCharacter character, GameData gameData) {
        super(character, "Poof of smoke", gameData);
    }

    @Override
    protected Sprite getAnimatedSprite() {
        return new AnimatedSprite("poofofsmoke", "wizardstuff.png", 0, 3, 32, 32,
                getActor(), 8, false);
    }
}
