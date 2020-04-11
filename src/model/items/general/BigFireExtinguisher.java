package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;

public class BigFireExtinguisher extends FireExtinguisher {
    public BigFireExtinguisher() {
        setName("Big " + getBaseName());
        this.setCost(getCost()*2);
        this.setMaxUses(12);
        this.setUses(12);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("bigfireext", "items.png", 41, this);
    }
}
