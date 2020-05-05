package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;
import model.characters.decorators.ColdProtection;
import model.characters.decorators.InstanceChecker;

/**
 * Created by erini02 on 13/04/16.
 */
public class Sweater extends TorsoSuit {
    public Sweater() {
        super("Sweater", 0.5, 25);
    }

    @Override
    public SuitItem clone() {
        return new Sweater();
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("sweaterworn", "suit2.png", 24, 11, this);
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {
        actionPerformer.setCharacter(new ColdProtection(actionPerformer.getCharacter()));
    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {
        actionPerformer.removeInstance(new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof ColdProtection;
            }
        });
    }

    @Override
    public boolean permitsOver() {
        return true;
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Nice and warm! Protects the wearer from getting to chilly.";
    }
}
