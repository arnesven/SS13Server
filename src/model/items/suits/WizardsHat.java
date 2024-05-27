package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;

public class WizardsHat extends HatItem {
    public WizardsHat() {
        super("Wizard's Hat", 0.35, 139);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("wizardshat", "hats.png", 5, this);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("wizardshatworn", "head2.png", 12, 4, this);
    }

    @Override
    public SuitItem clone() {
        return new WizardsHat();
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {

    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {

    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A point blue hat made of common felt cloth.";
    }
}
