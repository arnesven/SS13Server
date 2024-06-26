package model.items;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.general.GameItem;

public class SeveredArm extends BodyPart {
    private final String side;

    public SeveredArm(String side, Actor belongsTo) {
        super(belongsTo.getPublicName() + "'s " + side + " arm", 3.0, belongsTo);
        this.side = side;
    }

    @Override
    public GameItem clone() {
        return new SeveredArm(side, belongsTo);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (side.equals("left")) {
            return new Sprite("severedleftarm", "body_parts.png", 0, 0, this);
        }

        return new Sprite("severedrightarm", "body_parts.png", 1, 0, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A severed human limb.";
    }

    @Override
    public String getEatString() {
        return "finger";
    }
}
