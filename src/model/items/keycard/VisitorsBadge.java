package model.items.keycard;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;

public class VisitorsBadge extends IdentCardItem {

    public VisitorsBadge() {
        super("Visitor's Badge", 0.01, 10);
    }

    @Override
    public GameItem clone() {
        return new VisitorsBadge();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("visitorsbadge", "card2.png", 5, 7, this);
    }

    @Override
    protected String getExtendedDescription() {
        return "";
    }
}
