package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;

/**
 * Created by erini02 on 25/08/16.
 */
public class PackOfSmokes extends GameItem {
    public PackOfSmokes() {
        super("Pack of Smokes", 0.1, true, 20);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return  new Sprite("packofsmokes", "storage.png", 13);
    }

    @Override
    public GameItem clone() {
        return new PackOfSmokes();
    }
}
