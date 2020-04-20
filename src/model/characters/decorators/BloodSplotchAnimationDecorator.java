package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;
import model.events.animation.AnimatedSprite;

import java.util.ArrayList;
import java.util.List;

public class BloodSplotchAnimationDecorator extends CharacterDecorator {
    public BloodSplotchAnimationDecorator(GameCharacter character) {
        super(character, "bloodysplotch");
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        Sprite orig = super.getSprite(whosAsking);
        List<Sprite> sps = new ArrayList<>();
        sps.add(AnimatedSprite.blankAnimationSprite());
        sps.add(orig);
        sps.add(new AnimatedSprite("bloodsplotch", "effects2.png", 2, 23, 32, 32, null, 8, false));
        return new AnimatedSprite(orig.getName() + "bloodsplotch", sps, 8, false);
    }
}
