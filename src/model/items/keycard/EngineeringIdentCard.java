package model.items.keycard;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;

public class EngineeringIdentCard extends IdentCardItem {
    public EngineeringIdentCard() {
        super("Engineering Ident Card", 0.01, 20);
    }

    @Override
    public GameItem clone() {
        return new EngineeringIdentCard();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("engidentcard", "card2.png", 4, 7, this);
    }

    @Override
    protected String getExtendedDescription() {
        return "";
    }
}
