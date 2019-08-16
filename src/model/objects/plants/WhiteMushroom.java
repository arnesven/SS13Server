package model.objects.plants;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
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
public class WhiteMushroom extends Mushroom {
    public WhiteMushroom(Room position, SoilPatch sp) {
        super("White Mushrooms", position, sp);
    }

    @Override
    protected List<Sprite> getSpriteStages() {
        List<Sprite> sprs = new ArrayList<>();
        for (int c = 21; c <= 23; c++) {
            sprs.add(new Sprite("whitemushrooms"+c, "hydroponics.png", c, 4, this));
        }
        sprs.add(new Sprite("whitemushrooms"+0, "hydroponics.png", 0, 5, this));
        sprs.add(new Sprite("whitemushrooms"+1, "hydroponics.png", 1, 5, this));

        return sprs;
    }



    @Override
    public SeedsItem getSeeds() {
        return new MushroomSpores();
    }

    @Override
    protected FoodItem getAsItem() {
        return new WhiteMushroomItem();
    }

    private class WhiteMushroomItem extends FoodItem {
        public WhiteMushroomItem() {
            super("White Mushroom", 0.1, 35);
        }

        @Override
        public Sprite getSprite(Actor whosAsking) {
            return new Sprite("whitemushroomitem", "harvest.png", 6, this);
        }

        @Override
        public double getFireRisk() {
            return 0;
        }

        @Override
        public FoodItem clone() {
            return new WhiteMushroomItem();
        }

        @Override
        protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {

        }
    }
}
