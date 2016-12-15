package model.objects.plants;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.events.Event;
import model.events.PlantUpdater;
import model.events.ambient.RadiationStorm;
import model.items.foods.Tomato;
import model.items.seeds.SeedsItem;
import model.items.seeds.TomatoSeeds;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.TomatoNPC;
import model.objects.SoilPatch;

/**
 * Created by erini02 on 27/11/16.
 */
public class TomatoPlant extends Plant {
    public TomatoPlant(Room position, SoilPatch sp) {
        super("Tomato Plant", position, sp);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("tomatoplant", "hydroponics.png", 2, 1);
    }


    @Override
    public PlantUpdater getPlantUpdater(GameData gameData, SoilPatch s, Actor planter) {
        return new PlantUpdater(this) {
            int tomatosLeft = 3;
            int plantRound = gameData.getRound();
            boolean mutate = false;

            @Override
            protected void updatePlant(GameData gameData, Plant plant) {
                if (plantRound < gameData.getRound()) {
                    if (tomatosLeft > 0 && !plant.isBroken()) {
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
                    }
                }

                for (Event e : plant.getPosition().getEvents()) {
                    if (e instanceof RadiationStorm) {
                        mutate = true;
                    }
                }

            }

            @Override
            protected boolean isDone() {
                return tomatosLeft == 0;
            }

        };
    }

    @Override
    public SeedsItem getSeeds() {
        return new TomatoSeeds();
    }
}
