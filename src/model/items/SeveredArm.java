package model.items;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;

public class SeveredArm extends BodyPart {
    private final String side;
    private final Actor belongsTo;

    public SeveredArm(String side, Actor belongsTo) {
        super(belongsTo.getPublicName() + " " + side + " arm", 3.0);
        this.side = side;
        this.belongsTo = belongsTo;
    }

    @Override
    public GameItem clone() {
        return new SeveredArm(side, belongsTo);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (side.equals("left")) {
            return new Sprite("severedleftarm", "body_parts.png", 0, 0);
        }

        return new Sprite("severedrightarm", "body_parts.png", 1, 0);
    }
}
