package model.items.seeds;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.events.Event;
import model.events.PlantUpdater;
import model.events.ambient.RadiationStorm;
import model.items.foods.Tomato;
import model.items.general.GameItem;
import model.map.Room;
import model.npcs.NPC;
import model.npcs.TomatoNPC;
import model.objects.SoilPatch;
import model.objects.plants.Plant;
import model.objects.plants.TomatoPlant;

/**
 * Created by erini02 on 26/11/16.
 */
public abstract class SeedsItem extends GameItem {
    public SeedsItem(String name, int cost) {
        super(name, 0.05, true, cost);
    }


    public Plant doWhenPlanted(SoilPatch s, GameData gameData, Actor planter) {
        Plant pl = this.getPlant(s.getPosition(), gameData, planter, s);

        gameData.addEvent(pl.getPlantUpdater(gameData, s, planter));

        return pl;
    }


    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("orangeseeds", "seeds.png", 0, 0);
    }


    protected abstract Plant getPlant(Room position, GameData gameData,
                                      Actor planter, SoilPatch sp);
}
