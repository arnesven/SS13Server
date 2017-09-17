package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/09/17.
 */
public class MinerSpaceSuit extends SpaceSuit {
    public MinerSpaceSuit() {
        setName("Mining Space Suit");
    }

    @Override
    public MinerSpaceSuit clone() {
        return new MinerSpaceSuit();
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        Sprite body = new Sprite("minerspacesuitbody", "suit2.png", 15, 3);
        List<Sprite> sprs = new ArrayList<>();
        sprs.add(body);
        Sprite feet = new Sprite("minerspacesuitfeet", "head2.png", 2, 3, 32, 32, sprs);
        return feet;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("minerspacesuitfloor", "suits.png", 4);
    }
}
