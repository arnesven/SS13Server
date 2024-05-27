package model.items.mining;

import model.GameData;
import model.Player;
import model.items.general.GameItem;

/**
 * Created by erini02 on 17/09/17.
 */
public class RegolithShard extends OreShard {
    public RegolithShard() {
        super("Regolith Shard", 10);
    }

    @Override
    protected String getShardTypeName() {
        return "regolith";
    }

    @Override
    protected int getSpriteRow() {
        return 0;
    }

    @Override
    public int getCharge() {
        return 5;
    }

    @Override
    public GameItem clone() {
        return new RegolithShard();
    }
}
