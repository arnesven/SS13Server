package model.items;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.itemactions.UnpackItemAction;
import model.items.general.GameItem;
import model.items.general.UnpackableItem;
import util.MyRandom;

import java.util.ArrayList;

public class CapsulePrize extends UnpackableItem {
    private static int counter = 1;
    private final GameItem inner;
    private final int look;

    public CapsulePrize() {
        super("Capsule Prize #" + (counter++), 0.1, false, 0);
        this.inner = RandomItemManager.getRandomSearchItem();
        addInnerItem(inner);
        this.setWeight(inner.getWeight());
        this.look = MyRandom.nextInt(3) + 2;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("capsuleprize" + look, "arcade.png", look, 5, this);
    }

    @Override
    protected UnpackItemAction getUnpackAction(GameData gameData, ArrayList<Action> at, Actor cl) {
        return new UnpackItemAction("Open " + getPublicName(cl), this);
    }

    @Override
    public GameItem clone() {
        return new CapsulePrize();
    }
}
