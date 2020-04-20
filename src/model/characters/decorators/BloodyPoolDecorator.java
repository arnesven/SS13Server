package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;

import java.util.List;

public class BloodyPoolDecorator extends CharacterDecorator {
    public BloodyPoolDecorator(GameCharacter character) {
        super(character, "bloody pool");
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        Sprite sp = super.getSprite(whosAsking);
        Sprite bloody = new Sprite("bloodstainedcorpse", "blood.png", 3, 6, getActor());
        List<Sprite> list = List.of(bloody, sp);
        Sprite result = new Sprite(sp.getName()+"bloodstain", "human.png", 0, list, getActor());
        return result;
    }
}
