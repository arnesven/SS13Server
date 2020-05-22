package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;
import model.events.animation.AnimatedSprite;

import java.util.ArrayList;
import java.util.List;

public class BloodSplotchAnimationDecorator extends OneTurnAnimationDecorator {

    public BloodSplotchAnimationDecorator(GameCharacter character, GameData gameData) {
        super(character, "bloodysplotch", gameData);
    }

    @Override
    protected Sprite getAnimatedSprite() {
        return new AnimatedSprite("bloodsplotch", "effects2.png",
                2, 23, 32, 32, getActor(), 7, false);
    }
}
