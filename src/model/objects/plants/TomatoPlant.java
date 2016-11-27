package model.objects.plants;

import graphics.sprites.Sprite;
import model.Actor;
import model.Player;
import model.map.Room;

/**
 * Created by erini02 on 27/11/16.
 */
public class TomatoPlant extends Plant {
    public TomatoPlant(Room position) {
        super("Tomato Plant", position);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("tomatoplant", "hydroponics.png", 2, 1);
    }


}
