package model.items;

import model.items.general.GameItem;

public abstract class BodyPart extends GameItem {
    public BodyPart(String string, double weight) {
        super(string, weight, false, 0);
    }

}
