package model.items;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;

public class SeveredHead extends BodyPart {
    public SeveredHead(Actor belong) {
        super(belong.getPublicName() + "'s head", 3.0, belong);
    }

    @Override
    public GameItem clone() {
        return new SeveredHead(belongsTo);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("severedhead", "body_parts.png", 5, 0);
    }

    @Override
    public String getEatString() {
        return "eyeball";
    }
}
