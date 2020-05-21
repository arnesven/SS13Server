package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;

public class AnimalAppearanceDecorator extends CharacterDecorator {
    private final Sprite animalSprite;
    private final String pubName;

    public AnimalAppearanceDecorator(GameCharacter chara, String name, Sprite animalSprite, String pubName) {
        super(chara, name);
        this.animalSprite = animalSprite;
        this.pubName = pubName;

    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        Sprite sp = animalSprite;
        if (getActor().isDead()) {
            sp.setRotation(90);
        }
        return sp;
    }

    @Override
    public String getPublicName() {
        return pubName;
    }
}
