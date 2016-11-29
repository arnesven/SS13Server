package model.objects.plants;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.events.PlantUpdater;
import model.items.foods.Orange;
import model.items.seeds.OrangeSeeds;
import model.items.seeds.SeedsItem;
import model.map.Room;
import model.objects.SoilPatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 28/11/16.
 */
public class OrangePlant extends StagePlant {
    public OrangePlant(Room position, SoilPatch sp) {
        super("Orange Tree", position, sp, 4);
    }



    @Override
    protected List<Sprite> getSpriteStages() {
        List<Sprite> sprites = new ArrayList<>();
        for (int c = 14; c <= 17; c++) {
            sprites.add(new Sprite("orangetree" + c, "hydroponics.png", c, 13));
        }
        return sprites;
    }

    @Override
    public PlantUpdater getPlantUpdater(GameData gameData, SoilPatch s, Actor planter) {
        return new PlantUpdater(this) {
            int plantedInRound = gameData.getRound();
            @Override
            protected void updatePlant(GameData gameData, Plant plant) {
                if (gameData.getRound() != plantedInRound) {
                    increaseStage();
                }
                if (isMaxStage()) {
                    if (gameData.getRound() % 2 == 0 && !plant.isBroken()) {
                        plant.getPosition().addItem(new Orange(planter));
                    }
                }
            }

            @Override
            protected boolean isDone() {
                return false;
            }
        };
    }

    @Override
    public SeedsItem getSeeds() {
        return new OrangeSeeds();
    }
}
