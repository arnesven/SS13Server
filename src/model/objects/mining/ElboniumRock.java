package model.objects.mining;

import graphics.sprites.Sprite;
import model.Player;
import model.items.mining.ElboniumShard;
import model.items.mining.OreShard;
import model.map.rooms.Asteroid;
import model.map.rooms.Room;
import model.objects.general.GameObject;

/**
 * Created by erini02 on 17/09/17.
 */
public class ElboniumRock extends RockObject {
    public ElboniumRock(Room asteroid) {
        super("Elbonium Rock", asteroid);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isBroken()) {
            return super.getSprite(whosAsking);
        } else {
            return new Sprite("elboniumrock", "meteor.png", 0, 1, this);
        }
    }

    @Override
    protected OreShard getOre() {
        return new ElboniumShard();
    }
}
