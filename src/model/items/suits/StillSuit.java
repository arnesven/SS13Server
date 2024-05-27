package model.items.suits;

import graphics.sprites.RegularBlackShoesSprite;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StillSuit extends HeadShoesAndTorsoSuit {
    public StillSuit() {
        super("Stillsuit", 1.0, 500);
    }

    @Override
    public SuitItem clone() {
        return new StillSuit();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {

        return new Sprite("stillsuit", "suits.png", 18, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A special suit that recycles all the perspiration from the wearer into a drinking container.";
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        ArrayList<Sprite> list = new ArrayList<Sprite>();
        list.add(new RegularBlackShoesSprite());
        list.add(getExtraSprites().get(Equipment.HEAD_SLOT));
        return new Sprite("stillsuitworn", "suit2.png", 0, 20, 32, 32, list, this);

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
    protected Map<Integer, Sprite> getExtraSprites() {
        Map<Integer, Sprite> map = new HashMap<>();
        map.put(Equipment.FEET_SLOT, RegularBlackShoesSprite.EQ_SPRITE);
        map.put(Equipment.HEAD_SLOT, new Sprite("blackmask", "mask.png", 7, 11, this));

        return map;
    }
}
