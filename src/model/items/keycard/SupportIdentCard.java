package model.items.keycard;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;

public class SupportIdentCard extends IdentCardItem {
    public SupportIdentCard() {
        super("Support Ident Card", 0.01, 20);
    }

    @Override
    public GameItem clone() {
        return new SupportIdentCard();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("supportidentcard", "card2.png", 0, 7, this);
    }

    @Override
    protected String getExtendedDescription() {
        return "";
    }
}
