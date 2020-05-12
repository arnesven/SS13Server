package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;
import model.objects.general.CrateObject;

import java.util.ArrayList;
import java.util.List;

public class CarryingCrateDecorator extends CharacterDecorator {
    private final CrateObject crate;

    public CarryingCrateDecorator(GameCharacter character, CrateObject crate) {
        super(character, "Carrying " + crate.getPublicName(character.getActor()));
        this.crate = crate;
    }

    public CrateObject getCrate() {
        return crate;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        Sprite inner = getInner().getSprite(whosAsking);
        Sprite crateSprite = crate.getSprite(whosAsking);
        List<Sprite> sprs = new ArrayList<>();
        sprs.add(inner);
        Sprite crateShifted = new Sprite(crateSprite, "shiftup");
        crateShifted.shiftUpPx(10);
        sprs.add(crateShifted);
        return new Sprite(inner.getName()+"carry"+crate.getPublicName(whosAsking), "human.png", 0, sprs, getActor());
    }
}
