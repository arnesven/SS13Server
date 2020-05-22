package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.characters.general.GameCharacter;
import model.events.animation.AnimatedSprite;

import java.util.ArrayList;
import java.util.List;

public abstract class OneTurnAnimationDecorator extends CharacterDecorator {

    private final int roundSet;
    private Sprite orig;

    public OneTurnAnimationDecorator(GameCharacter chara, String name, GameData gameData) {
        super(chara, name);
        this.orig = chara.getUnanimatedSprite(getActor());
        this.roundSet = gameData.getRound();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        Sprite orig = getInner().getSprite(whosAsking);
        List<Sprite> sps = new ArrayList<>();
        sps.add(AnimatedSprite.blankAnimationSprite());
        sps.add(orig);
        sps.add(getAnimatedSprite());
        Sprite result = new AnimatedSprite(orig.getName() + "bloodsplotch", sps, 7, false);
        result.setObjectRef(getActor());
        return result;
    }

    protected abstract Sprite getAnimatedSprite();


    @Override
    public Sprite getUnanimatedSprite(Actor whosAsking) {
        return orig;
    }


    @Override
    public void doAfterActions(GameData gameData) {
        super.doAfterActions(gameData);
        if (roundSet < gameData.getRound()) {
            getActor().removeInstance((GameCharacter gc) -> gc == this);
        }
    }
}
