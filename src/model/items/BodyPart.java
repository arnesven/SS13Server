package model.items;

import model.Actor;
import model.items.general.GameItem;

public abstract class BodyPart extends GameItem {
    protected final Actor belongsTo;

    public BodyPart(String string, double weight, Actor belongsTo) {
        super(string, weight, false, 0);
        this.belongsTo = belongsTo;
    }

}
