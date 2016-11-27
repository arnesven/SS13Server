package model.items.seeds;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.items.general.GameItem;
import model.objects.SoilPatch;
import model.objects.plants.Plant;

/**
 * Created by erini02 on 26/11/16.
 */
public abstract class SeedsItem extends GameItem {
    public SeedsItem(String name, int cost) {
        super(name, 0.05, true, cost);
    }


    public abstract Plant doWhenPlanted(SoilPatch s, GameData gameData, Actor planter);
}
