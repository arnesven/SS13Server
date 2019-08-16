package model.items.suits;

import graphics.sprites.RegularBlackShoesSprite;
import graphics.sprites.Sprite;
import model.Actor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 15/11/16.
 */
public class MerchantSuit extends SuitItem {
    public MerchantSuit() {
        super("Merchant's Suit", 0.5, 49);
    }

    @Override
    public SuitItem clone() {
        return new MerchantSuit();
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {

    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {

    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        List<Sprite> list = new ArrayList<>();
        list.add(new RegularBlackShoesSprite());
        return new Sprite("merchantssuitworn", "uniform2.png", 32, 26, 32, 32, list, this);
    }

    @Override
    public boolean permitsOver() {
        return false;
    }
}
