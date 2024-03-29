package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.PiercingProtection;
import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 26/04/16.
 */
public class SecOffsVest extends TorsoSuit {
    public SecOffsVest() {
        super("Vest", 1.0, 139);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("secoffsvest", "suits.png", 2, this);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("secoffsvestworn", "suit.png", 10, this);
    }

    @Override
    public SuitItem clone() {
        return new SecOffsVest();
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {
        actionPerformer.setCharacter(new PiercingProtection(actionPerformer.getCharacter()));
    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {
        if (actionPerformer.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof PiercingProtection)) {
            actionPerformer.removeInstance(new InstanceChecker() {
                @Override
                public boolean checkInstanceOf(GameCharacter ch) {
                    return ch instanceof PiercingProtection;
                }
            });
        }

    }

    @Override
    public boolean permitsOver() {
        return true;
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Protects the wearer somewhat from physical damage, like gunshots, stabbings etc.";
    }
}
