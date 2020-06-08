package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.OxyMaskDecorator;
import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 30/04/16.
 */
public class OxygenMask extends HeadGear {
    public OxygenMask() {
        super("Oxygen Mask", 0.5, 69);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("oxymask", "hats.png", 2, this);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("oxymaskworn", "mask.png", 8, this);
    }

    @Override
    public SuitItem clone() {
        return new OxygenMask();
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {
        actionPerformer.setCharacter(new OxyMaskDecorator(actionPerformer.getCharacter()));
    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {
        if (actionPerformer.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof OxyMaskDecorator)) {
            actionPerformer.removeInstance(new InstanceChecker() {
                @Override
                public boolean checkInstanceOf(GameCharacter ch) {
                    return ch instanceof OxyMaskDecorator;
                }
            });
        }
    }

    @Override
    public boolean permitsOver() {
        return false;
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Supplies the wearer with a flow of oxygen. WARNING: this mask does not provide an adequate protection for zero or near-zero pressure environments.";
    }
}
