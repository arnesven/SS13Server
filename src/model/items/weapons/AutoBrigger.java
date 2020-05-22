package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;

public class AutoBrigger extends Weapon {

    public AutoBrigger() {
        super("AutoBrigger", 0.95, 0.0, false, 0.5, false, 330);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("autobrigger", "items.png", 26, this);
    }


    @Override
    public GameItem clone() {
        return new AutoBrigger();
    }
}
