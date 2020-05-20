package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;

import java.util.ArrayList;
import java.util.List;

public class SpriteOverlayDecorator extends CharacterDecorator {
    private final Sprite overSprite;

    public SpriteOverlayDecorator(GameCharacter chara, String name, Sprite sp) {
        super(chara, name);
        this.overSprite = sp;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        List<Sprite> sp = new ArrayList<>();
        Sprite base = super.getSprite(whosAsking);
        sp.add(base);
        sp.add(overSprite);
        Sprite sprt = new Sprite(overSprite.getName()+base.getName(), "human.png", 0, sp, getActor());
        return sprt;
    }

}
