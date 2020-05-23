package model.characters.decorators;

import graphics.sprites.Sprite;
import model.GameData;
import model.characters.general.GameCharacter;
import model.events.animation.AnimatedSprite;

public class PoofOfSmokeAnimationDecorator extends OneTurnAnimationDecorator {
    private int row;

    public PoofOfSmokeAnimationDecorator(GameCharacter character, GameData gameData, boolean green) {
        super(character, "Poof of smoke", gameData);
        this.row = 3;
        if (green) {
            row = 4;
        }
    }

    public PoofOfSmokeAnimationDecorator(GameCharacter character, GameData gameData) {
        this(character, gameData, false);
    }

    @Override
    protected Sprite getAnimatedSprite() {
        return new AnimatedSprite("poofofsmoke", "wizardstuff.png", 0, row, 32, 32,
                getActor(), 8, false);
    }
}
