package model.objects.plants;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.events.PlantUpdater;
import model.items.seeds.MushroomSpores;
import model.items.seeds.SeedsItem;
import model.map.Room;
import model.objects.SoilPatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 28/11/16.
 */
public class WhiteMushroom extends Mushroom {
    public WhiteMushroom(Room position) {
        super("White Mushrooms", position);
    }

    @Override
    protected List<Sprite> getSpriteStages() {
        List<Sprite> sprs = new ArrayList<>();
        for (int c = 21; c <= 23; c++) {
            sprs.add(new Sprite("whitemushrooms"+c, "hydroponics.png", c, 4));
        }
        sprs.add(new Sprite("whitemushrooms"+0, "hydroponics.png", 0, 5));
        sprs.add(new Sprite("whitemushrooms"+1, "hydroponics.png", 1, 5));

        return sprs;
    }



    @Override
    public SeedsItem getSeeds() {
        return new MushroomSpores();
    }
}
