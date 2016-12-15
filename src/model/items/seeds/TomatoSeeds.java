package model.items.seeds;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.items.general.GameItem;
import model.map.rooms.Room;
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
    protected Plant getPlant(Room position, GameData gameData, Actor planter, SoilPatch sp) {
        TomatoPlant pl = new TomatoPlant(position, sp);
        return pl;
    }
}
