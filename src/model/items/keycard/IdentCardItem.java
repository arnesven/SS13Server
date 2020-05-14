package model.items.keycard;

import model.Actor;
import model.Target;
import model.items.general.GameItem;
import model.items.general.KeyCard;

public class IdentCardItem extends GameItem {

    private Actor belongsTo;

    public IdentCardItem(String name, double weight, int cost) {
        super(name, weight, cost);
        this.belongsTo = null;
    }

    @Override
    public GameItem clone() {
        return new IdentCardItem(getBaseName(), getWeight(), getCost());
    }

    @Override
    public void gotGivenTo(Actor to, Target from) {
        super.gotGivenTo(to, from);
        if (belongsTo == null) {
            belongsTo = to;
        }
    }

    public static IdentCardItem findIdentCard(Actor forWhom) {
        for (GameItem it : forWhom.getItems()) {
            if (it instanceof IdentCardItem) {
                return (IdentCardItem) it;
            }
        }
        return null;
    }

    public Actor getBelongsTo() {
        return belongsTo;
    }
}
