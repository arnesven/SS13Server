package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.decorators.*;
import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 11/11/16.
 */
public class PirateCaptainOutfit extends SuitItem {
    public PirateCaptainOutfit() {
        super("Pirate Captain's Armor", 5.0, 699);
    }

    @Override
    public SuitItem clone() {
        return new PirateCaptainOutfit();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("piratecaptainsarmor", "uniforms.png", 7, 1, this);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        Sprite spr = new Sprite("piratecaptainsarmorworn", "suit.png", 60, this);
        spr.addToOver(new Sprite("piratecapshead", "head.png", 9, 1, this));
        return spr;
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {
        actionPerformer.setCharacter(new PiercingProtection(actionPerformer.getCharacter()));
        actionPerformer.setCharacter(new SpaceProtection(actionPerformer.getCharacter()));
        actionPerformer.setCharacter(new NameChangeDecorator(actionPerformer.getCharacter(), "Pirate Captain"));
    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {
        actionPerformer.removeInstance((GameCharacter ch) -> ch instanceof PiercingProtection);
        actionPerformer.removeInstance((GameCharacter ch) -> ch instanceof SpaceProtection);
        actionPerformer.removeInstance((GameCharacter gc) -> gc instanceof NameChangeDecorator);
    }

    @Override
    public boolean permitsOver() {
        return false;
    }
}
