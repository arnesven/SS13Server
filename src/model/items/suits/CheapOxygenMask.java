package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.decorators.LimitedOxyMaskDecorator;

public class CheapOxygenMask extends OxygenMask {

    public CheapOxygenMask() {
        setCost(15);
        setName("Cheap Oxygen Mask");
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("cheapoxymaskworn", "mask.png", 10, 2, this);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("cheapoxymask", "masks.png", 2, 2, this);
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {
        actionPerformer.setCharacter(new LimitedOxyMaskDecorator(actionPerformer.getCharacter(), this));
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return super.getDescription(gameData, performingClient) +
                " This cheap knock-off has a very limited oxygen supply.";
    }
}
