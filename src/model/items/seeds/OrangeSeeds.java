package model.items.seeds;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.items.general.GameItem;
import model.map.Room;
import model.objects.SoilPatch;
import model.objects.plants.OrangePlant;
import model.objects.plants.Plant;

/**
 * Created by erini02 on 28/11/16.
 */
public class OrangeSeeds extends SeedsItem {
    public OrangeSeeds() {
        super("Orange Seeds", 10);
    }

    @Override
    protected Plant getPlant(Room position, GameData gameData, Actor planter, SoilPatch sp) {
        return new OrangePlant(position, sp);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("orangeseeds", "seeds.png", 3, 4);
    }

    @Override
    public GameItem clone() {
        return new OrangeSeeds();
    }
}
