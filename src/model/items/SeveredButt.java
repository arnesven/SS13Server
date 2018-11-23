package model.items;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;

public class SeveredButt extends BodyPart {
    public SeveredButt(Actor belong) {
        super(belong.getPublicName() + "'s butt", 3.0, belong);
    }

    @Override
    public GameItem clone() {
        return new SeveredButt(belongsTo);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("severedleftleg", "body_parts.png", 4, 0);
    }

    @Override
    public String getEatString() {
        return "calamari";
    }
}
