package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;

/**
 * Created by erini02 on 26/11/16.
 */
public class ActorsAshes extends GameItem {
    private final Actor belongedTo;

    public ActorsAshes(Actor targetAsActor) {
        super(targetAsActor.getBaseName() + "'s Ashes", 0.5, false, 1);
        this.belongedTo = targetAsActor;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite(belongedTo.getBaseName() + "sashes", "drinks.png", 11, this);
    }

    @Override
    public GameItem clone() {
        return new ActorsAshes(belongedTo);
    }
}
