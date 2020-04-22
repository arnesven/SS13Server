package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;
import model.events.animation.AnimatedSprite;

import java.util.ArrayList;
import java.util.List;

public class BloodSplotchAnimationDecorator extends CharacterDecorator {
    private final int roundSet;
    private Sprite orig;

    public BloodSplotchAnimationDecorator(GameCharacter character, GameData gameData) {
        super(character, "bloodysplotch");
        this.roundSet = gameData.getRound();
        this.orig = character.getUnanimatedSprite(getActor());
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        Sprite orig = getInner().getSprite(whosAsking);
        List<Sprite> sps = new ArrayList<>();
        sps.add(AnimatedSprite.blankAnimationSprite());
        sps.add(orig);
        sps.add(new AnimatedSprite("bloodsplotch", "effects2.png", 2, 23, 32, 32, getActor(), 7, false));
        Sprite result = new AnimatedSprite(orig.getName() + "bloodsplotch", sps, 7, false);
        result.setObjectRef(getActor());
        return result;
    }

    @Override
    public void doAfterActions(GameData gameData) {
        super.doAfterActions(gameData);
        if (roundSet < gameData.getRound()) {
            getActor().removeInstance((GameCharacter gc) -> gc == this);
        }
    }

    @Override
    public Sprite getUnanimatedSprite(Actor whosAsking) {
        return orig;
    }
}
