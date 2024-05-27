package model.items.mining;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.general.GameItem;
import util.MyRandom;

/**
 * Created by erini02 on 17/09/17.
 */
public abstract class OreShard extends GameItem {
    private final int rand;

    public OreShard(String string, int cost) {
        super(string, 1.0, false, cost);
        this.rand = MyRandom.nextInt(3);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite(getShardTypeName() + "shard" + rand, "shards.png", rand, getSpriteRow(), this);
    }

    protected abstract String getShardTypeName();

    protected abstract int getSpriteRow();

    public abstract int getCharge();

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "An ore shard of type " + getShardTypeName() + ".<br/>Charges: " + getCharge() + ".";
    }

}
