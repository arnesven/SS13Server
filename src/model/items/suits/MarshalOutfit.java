package model.items.suits;

import graphics.sprites.RegularBlackShoesSprite;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by erini02 on 17/11/16.
 */
public class MarshalOutfit extends TorsoAndShoesSuit {
    public MarshalOutfit() {
        super("Marshal's Outfit", 0.5, 130);
    }

    @Override
    public SuitItem clone() {
        return new MarshalOutfit();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "An outfit worn by officers of the GFPD.";
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        List<Sprite> list = new ArrayList<>();
        list.add(getOtherSprites().get(Equipment.FEET_SLOT));
        return new Sprite("marshalssuitworn", "uniform2.png", 22, 20, 32, 32, list, this);
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {

    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {

    }

    @Override
    public boolean permitsOver() {
        return false;
    }

    @Override
    protected Map<Integer, Sprite> getOtherSprites() {
        Map<Integer, Sprite> map = new HashMap<>();
        map.put(Equipment.FEET_SLOT, RegularBlackShoesSprite.EQ_SPRITE);
        return map;
    }
}
