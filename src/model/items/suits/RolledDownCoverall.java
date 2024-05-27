package model.items.suits;

import graphics.sprites.RegularBlackShoesSprite;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;

import java.util.HashMap;
import java.util.Map;

public abstract class RolledDownCoverall extends TorsoAndShoesSuit {
    public RolledDownCoverall(String colorName) {
        super(colorName + " Coverall", 0.8, 15);
    }

    @Override
    public Sprite getWornSprite(Actor whosAsking) {
        return OutFit.makeOutfit("rolleddowncoverall"+getSpriteCol()+""+getSpriteRow(),
                "uniform2.png", getSpriteCol(), getSpriteRow(), this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Coveralls which can be rolled down with ease.";
    }

    protected abstract int getSpriteCol();
    protected abstract int getSpriteRow();

    @Override
    protected Map<Integer, Sprite> getOtherSprites() {
        Map<Integer, Sprite> map = new HashMap<>();
        map.put(Equipment.FEET_SLOT, RegularBlackShoesSprite.EQ_SPRITE);
        return map;
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {

    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {

    }
}
