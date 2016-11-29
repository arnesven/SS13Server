package model.items;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.weapons.Knife;

/**
 * Created by erini02 on 29/11/16.
 */
public class Scalpel extends Knife {

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("scalpel", "surgery2.png", 1);
    }
}
