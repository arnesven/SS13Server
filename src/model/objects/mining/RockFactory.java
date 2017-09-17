package model.objects.mining;

import model.map.rooms.Asteroid;
import model.objects.general.GameObject;
import model.objects.mining.ElboniumRock;
import model.objects.mining.RegolithRock;
import model.objects.mining.UnobtainiumRock;
import util.MyRandom;

/**
 * Created by erini02 on 17/09/17.
 */
public class RockFactory {
    public static GameObject randomRock(Asteroid asteroid) {
        double seed = MyRandom.nextDouble();
        if (seed < 0.10) {
            return new UnobtainiumRock(asteroid);
        } else if (seed < 0.30) {
            return new ElboniumRock(asteroid);
        }
        return new RegolithRock(asteroid);
    }
}
