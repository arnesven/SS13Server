package model.map.rooms;

import model.objects.mining.RockFactory;
import util.Logger;
import util.MyRandom;

/**
 * Created by erini02 on 16/09/17.
 */
public class Asteroid extends Room {
    public Asteroid(int id, int x, int y, int w, int h) {
        super(id, "Asteroid " + id, "AST", x, y, w, h, new int[]{}, new double[]{}, RoomType.hall);

        for (double d = 0.9; d > MyRandom.nextDouble(); d = d/2.0) {
            addObject(RockFactory.randomRock(this));
        }

    }
}
