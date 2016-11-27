package model.items.seeds;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.events.Event;
import model.events.PlantUpdater;
import model.events.ambient.RadiationStorm;
import model.items.foods.Tomato;
import model.items.general.GameItem;
import model.npcs.NPC;
import model.npcs.TomatoNPC;
import model.objects.SoilPatch;
import model.objects.plants.Plant;
import model.objects.plants.TomatoPlant;

/**
 * Created by erini02 on 26/11/16.
 */
public class TomatoSeeds extends SeedsItem {
    public TomatoSeeds() {
        super("Tomato Seeds", 2);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("tomatoseeds", "seeds.png", 8);
    }



    @Override
    public GameItem clone() {
        return new TomatoSeeds();
    }

    @Override
    public Plant doWhenPlanted(SoilPatch s, GameData gameData, Actor planter) {
        TomatoPlant pl = new TomatoPlant(s.getPosition());
        gameData.addEvent(new PlantUpdater(pl) {
            int tomatosLeft = 3;
            int plantRound = gameData.getRound();
            boolean mutate = false;

            @Override
            protected void updatePlant(GameData gameData, Plant plant) {
                if (plantRound < gameData.getRound()) {
                    if (tomatosLeft > 0) {
                        if (!mutate) {
                            plant.getPosition().addItem(new Tomato(planter));
                        } else {
                            NPC tomatoNPC = new TomatoNPC(plant.getPosition());
                            gameData.addNPC(tomatoNPC);
                        }
                        tomatosLeft--;
                    } else {
                        s.clearPlant();
                        s.getPosition().addObject(new SoilPatch(s.getPosition()));
                        this.setShouldBeRemoved(true);
                    }
                }

                for (Event e : plant.getPosition().getEvents()) {
                    if (e instanceof RadiationStorm) {
                        mutate = true;
                    }
                }

            }
        });

        return pl;
    }
}
