package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.characters.general.GameCharacter;
import model.events.animation.AnimatedSprite;
import model.items.spellbooks.SpellBook;

import java.util.ArrayList;
import java.util.List;

public class SpellCastingDecorator extends TalkingDecorator {
    private final SpellBook spellBook;

    public SpellCastingDecorator(GameCharacter character, SpellBook spellBook) {
        super(character, true, false);
        this.spellBook = spellBook;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        Sprite orig = super.getSprite(whosAsking);
        List<Sprite> sps = new ArrayList<>();
        sps.add(AnimatedSprite.blankAnimationSprite());
        sps.add(orig);
        sps.add(spellBook.getCastingEffect());
        Sprite result = new AnimatedSprite(orig.getName() + "tingle", sps, spellBook.getCastingEffect().getFrames(), true);
        result.setObjectRef(getActor());
        return result;
    }

    @Override
    public void doAfterActions(GameData gameData) {
        super.doAfterActions(gameData);
        getActor().removeInstance((GameCharacter gc) -> gc == this);
    }
}
