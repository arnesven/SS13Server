package model.items.seeds;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.items.general.GameItem;
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
        return null;
    }

    @Override
    public Plant doWhenPlanted(SoilPatch s, GameData gameData) {
        return new TomatoPlant(s.getPosition());
    }
}
