package model.items.keycard;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.items.general.GameItem;
import model.items.general.KeyCard;

public abstract class IdentCardItem extends GameItem {

    private Actor belongsTo;

    public IdentCardItem(String name, double weight, int cost) {
        super(name, weight, cost);
        this.belongsTo = null;
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

    @Override
    public final String getDescription(GameData gameData, Player performingClient) {
        if (belongsTo == null) {
            return getExtendedDescription();
        }
        return "An ID card belonging to " + belongsTo.getBaseName() + ". " + getExtendedDescription();
    }

    protected abstract String getExtendedDescription();
}
