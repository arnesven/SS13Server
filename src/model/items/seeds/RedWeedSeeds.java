package model.items.seeds;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.items.general.GameItem;
import model.map.rooms.Room;
import model.objects.SoilPatch;
import model.objects.plants.RedWeed;
import model.objects.plants.Plant;

/**
 * Created by erini02 on 28/11/16.
 */
public class RedWeedSeeds extends SeedsItem {
    public RedWeedSeeds() {
        super("Red Weed Seeds", 10);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("redweedseeds", "seeds.png", 6, 2);
    }


    @Override
    public GameItem clone() {
        return new RedWeedSeeds();
    }

    @Override
    protected Plant getPlant(Room position, GameData gameData, Actor planter, SoilPatch sp) {
        return new RedWeed(position, sp);
    }
}
