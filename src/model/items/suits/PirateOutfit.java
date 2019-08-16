package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.decorators.DisguisedAs;
import model.characters.decorators.InstanceChecker;
import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 26/04/16.
 */
public class PirateOutfit extends SuitItem {
    private final int num;

    public PirateOutfit(int num) {
        super("Pirate Outfit", 1.0, 20);
        this.num = num;
    }

    @Override
    public SuitItem clone() {
        return new PirateOutfit(num);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("piratesoutfit", "uniforms.png", 2, 2, this);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        Sprite spr = OutFit.makeOutfit("piratesoutfitworn", "uniform2.png", 2, 32, this);
        spr.addToOver(new Sprite("piratemask", "mask.png", 13, 1, this));
        return spr;
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {
        actionPerformer.setCharacter(new DisguisedAs(actionPerformer.getCharacter(), "Pirate #" + num));
    }


    @Override
    public void beingTakenOff(Actor actionPerformer) {
        actionPerformer.removeInstance(new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof DisguisedAs;
            }
        });
    }


    @Override
    public boolean permitsOver() {
        return false;
    }
}
