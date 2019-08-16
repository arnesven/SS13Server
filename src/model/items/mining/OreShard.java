package model.items.mining;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;
import util.MyRandom;

/**
 * Created by erini02 on 17/09/17.
 */
public abstract class OreShard extends GameItem {
    public OreShard(String string, int cost) {
        super(string, 1.0, false, cost);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        int rand = MyRandom.nextInt(3);

        return new Sprite(getShardTypeName() + "shard" + rand, "shards.png", rand, getSpriteRow(), this);
    }

    protected abstract String getShardTypeName();

    protected abstract int getSpriteRow();

    public abstract int getCharge();

}
