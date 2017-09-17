package model.items.mining;

import model.items.general.GameItem;

/**
 * Created by erini02 on 17/09/17.
 */
public class ElboniumShard extends OreShard {
    public ElboniumShard() {
        super("Elbonium Shard", 100);
    }

    @Override
    protected String getShardTypeName() {
        return "elbonium";
    }

    @Override
    protected int getSpriteRow() {
        return 2;
    }

    @Override
    public GameItem clone() {
        return new ElboniumShard();
    }
}
