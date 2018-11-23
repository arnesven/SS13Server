package model.items;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;

public class SeveredLeg extends BodyPart {
    private final String side;

    public SeveredLeg(String side, Actor belong) {
        super(belong.getPublicName() + "'s " + side + " leg", 4.0, belong);
        this.side = side;
    }

    @Override
    public GameItem clone() {
        return new SeveredLeg(side, belongsTo);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (side.equals("left")) {
            return new Sprite("severedleftleg", "body_parts.png", 2, 0);
        }

        return new Sprite("severedrightleg", "body_parts.png", 3, 0);
    }

    @Override
    public String getEatString() {
        return "toe";
    }
}
