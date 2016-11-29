package model.items.seeds;

import model.Actor;
import model.GameData;
import model.items.general.GameItem;
import model.map.Room;
import model.objects.plants.Plant;
import model.objects.plants.RedMushroom;
import model.objects.plants.WhiteMushroom;
import util.MyRandom;

/**
 * Created by erini02 on 28/11/16.
 */
public class MushroomSpores extends SeedsItem {
    public MushroomSpores() {
        super("Mushroom Spores", 25);
    }

    @Override
    protected Plant getPlant(Room position, GameData gameData, Actor planter) {
        return randomMushroom(position, gameData, planter);
    }

    private Plant randomMushroom(Room position, GameData gameData, Actor planter) {

        if (MyRandom.nextDouble() < 0.5) {
            return new WhiteMushroom(position);
        }

        return new RedMushroom(position);
    }

    @Override
    public GameItem clone() {
        return new MushroomSpores();
    }
}
