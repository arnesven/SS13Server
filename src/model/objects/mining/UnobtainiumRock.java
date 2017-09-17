package model.objects.mining;

import graphics.sprites.Sprite;
import model.Player;
import model.items.mining.OreShard;
import model.items.mining.UnobtainiumShard;
import model.map.rooms.Asteroid;
import model.objects.general.GameObject;

/**
 * Created by erini02 on 17/09/17.
 */
public class UnobtainiumRock extends RockObject {
    public UnobtainiumRock(Asteroid asteroid) {
        super("Unobtainium Rock", asteroid);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isBroken()) {
            return super.getSprite(whosAsking);
        } else {
            return new Sprite("unobtaniumrock", "meteor.png", 2, 0);
        }
    }

    @Override
    protected OreShard getOre() {
        return new UnobtainiumShard();
    }

}
