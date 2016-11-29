package model.objects.plants;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.characters.decorators.PoisonedDecorator;
import model.items.foods.FoodItem;
import model.items.seeds.MushroomSpores;
import model.items.seeds.SeedsItem;
import model.map.Room;
import model.objects.SoilPatch;

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
            sprs.add(new Sprite("redmushroom" + c, "hydroponics.png", c, 5));
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

    private class RedMushroomItem extends FoodItem {
        public RedMushroomItem() {
            super("Red Mushroom", 0.1, 75);
        }

        @Override
        public double getFireRisk() {
            return 0;
        }

        @Override
        public FoodItem clone() {
            return new RedMushroomItem();
        }

        @Override
        public Sprite getSprite(Actor whosAsking) {
            return new Sprite("redmushroomitem", "harvest.png", 5);
        }

        @Override
        protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
            eatenBy.setCharacter(new PoisonedDecorator(eatenBy.getCharacter()));
        }
    }
}
