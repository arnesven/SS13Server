package model.items.seeds;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.general.GameItem;
import model.map.rooms.Room;
import model.objects.general.SoilPatch;
import model.objects.plants.Plant;

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




    protected abstract Plant getPlant(Room position, GameData gameData,
                                      Actor planter, SoilPatch sp);

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Seeds which, when planted will sprout " + plantName() + ".";
    }

    protected abstract String plantName();
}
