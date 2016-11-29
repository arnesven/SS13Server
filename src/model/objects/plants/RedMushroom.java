package model.objects.plants;

import graphics.sprites.Sprite;
import model.items.seeds.MushroomSpores;
import model.items.seeds.SeedsItem;
import model.map.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 28/11/16.
 */
public class RedMushroom extends Mushroom {
    public RedMushroom(Room position) {
        super("Red Mushroom", position);
    }

    @Override
    protected List<Sprite> getSpriteStages() {
        List<Sprite> sprs = new ArrayList<>();
        for (int c = 2; c <= 6; ++c) {
            sprs.add(new Sprite("redmushroom" + c, "hydroponics.png", c, 5));
        }
        return sprs;
    }

    @Override
    public SeedsItem getSeeds() {
        return new MushroomSpores();
    }
}
