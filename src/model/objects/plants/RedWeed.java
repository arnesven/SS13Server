package model.objects.plants;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.events.PlantUpdater;
import model.items.seeds.RedWeedSeeds;
import model.items.seeds.SeedsItem;
import model.map.rooms.Room;
import model.objects.SoilPatch;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 28/11/16.
 */
public class RedWeed extends StagePlant {

    public RedWeed(Room position, SoilPatch sp) {
        super("Red Weed", position, sp, 6);
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
                for (Room r : plant.getPosition().getNeighborList()) {
                    boolean hadSoil = false;
                    for (GameObject ob : r.getObjects()) {
                        if (ob instanceof SoilPatch) {
                            hadSoil = true;
                            if (!((SoilPatch) ob).isPlanted()) {
                                ((SoilPatch) ob).plant(getSeeds(), gameData, planter);
                            }
                        }
                    }
                    if (!hadSoil) {
                        r.addObject(new SoilPatch(r));
                    }
                }

            }

            @Override
            protected boolean isDone() {
                return isMaxStage();
            }


        };
    }

    @Override
    public SeedsItem getSeeds() {
        return new RedWeedSeeds();
    }

    @Override
    protected List<Sprite> getSpriteStages() {
        List<Sprite> list = new ArrayList<>();
        for (int c = 18; c <= 23; c++) {
            list.add(new Sprite("redweedstage" + c, "hydroponics.png", c, 6));
        }
        return list;
    }
}
