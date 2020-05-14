package model.items.general;

import model.Actor;
import model.items.keycard.IdentCardItem;
import model.map.doors.ElectricalDoor;

public abstract class KeyCard extends IdentCardItem {

    public KeyCard(String string) {
        super(string, 0.01, 129);
    }

    public static KeyCard findKeyCard(Actor forWhom) {
        for (GameItem it : forWhom.getItems()) {
            if (it instanceof KeyCard) {
                return (KeyCard) it;
            }
        }
        return null;
    }

    public abstract boolean canOpenDoor(ElectricalDoor d);

}
