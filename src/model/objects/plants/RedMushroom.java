package model.objects.plants;

import graphics.sprites.Sprite;
import model.items.foods.FoodItem;
import model.items.seeds.MushroomSpores;
import model.items.seeds.SeedsItem;
import model.map.rooms.Room;
import model.objects.general.SoilPatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 28/11/16.
 */
public class RedMushroom extends Mushroom {
    public RedMushroom(Room position, SoilPatch sp) {
        super("Red Mushroom", position, sp);
    }

    @Override
    protected List<Sprite> getSpriteStages() {
        List<Sprite> sprs = new ArrayList<>();
        for (int c = 2; c <= 6; ++c) {
            sprs.add(new Sprite("redmushroom" + c, "hydroponics.png", c, 5, this));
        }
        return sprs;
    }

    @Override
    public SeedsItem getSeeds() {
        return new MushroomSpores();
    }

    @Override
    protected FoodItem getAsItem() {
        return new RedMushroomItem();
    }

}
