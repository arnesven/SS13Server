package model.objects.plants;

import model.Actor;
import model.GameData;
import model.events.Event;
import model.events.PlantUpdater;
import model.items.seeds.SeedsItem;
import model.map.Room;
import model.objects.SoilPatch;
import model.objects.general.GameObject;

/**
 * Created by erini02 on 26/11/16.
 */
public abstract class Plant extends GameObject {
    public Plant(String name, Room position) {
        super(name, position);
    }

    public abstract PlantUpdater getPlantUpdater(GameData gameData,
                                                 SoilPatch s, Actor planter);

    public abstract SeedsItem getSeeds();
}
