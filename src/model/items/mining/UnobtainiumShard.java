package model.items.mining;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;
import util.MyRandom;

/**
 * Created by erini02 on 17/09/17.
 */
public class UnobtainiumShard extends OreShard {
    public UnobtainiumShard() {
        super("Unobtainium Shard", 400);
    }


    @Override
    public GameItem clone() {
        return new UnobtainiumShard();
    }

    @Override
    protected String getShardTypeName() {
        return "unobtainium";
    }

    @Override
    protected int getSpriteRow() {
        return 1;
    }

    @Override
    public int getCharge() {
        return 30;
    }
}
