package model.items;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.general.GameItem;

/**
 * Created by erini02 on 12/10/16.
 */
public class Brain extends GameItem {
    private final Actor belongsTo;
    private final Actor cutOutBy;

    public Brain(Actor belongsTo, Actor cutOutBy) {
        super(belongsTo.getCharacter().getPublicName().replace("(dead)", "") + "'s Brain", 1.0, 0);
        this.belongsTo = belongsTo;
        this.cutOutBy = cutOutBy;
    }

    @Deprecated
    public Brain() {
        super("Brain", 0.0, false, 0);
        belongsTo = null;
        cutOutBy = null;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new  Sprite("brain", "surgery.png", 2, 1, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A squishy human brain.";
    }

    @Override
    public GameItem clone() {
        return new Brain(belongsTo, cutOutBy);
    }

    public String getProgramName() {
        return belongsTo.getPublicName().replace("(dead)", "") + " ROM";
    }

    public Actor getBelongsTo() {
        return belongsTo;
    }
}
