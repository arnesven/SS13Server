package model.objects.plants;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.events.Event;
import model.events.PlantUpdater;
import model.events.damage.Damager;
import model.events.damage.RadiationDamage;
import model.items.seeds.SeedsItem;
import model.map.Room;
import model.objects.SoilPatch;
import model.objects.general.BreakableObject;
import model.objects.general.GameObject;

import java.util.ArrayList;

/**
 * Created by erini02 on 26/11/16.
 */
public abstract class Plant extends BreakableObject {
    public Plant(String name, Room position) {
        super(name, 0.5, position);
    }

    public abstract PlantUpdater getPlantUpdater(GameData gameData,
                                                 SoilPatch s, Actor planter);

    public abstract SeedsItem getSeeds();


    @Override
    public void beExposedTo(Actor performingClient, Damager damage) {
        if (damage instanceof RadiationDamage) {
            return;
        }
        super.beExposedTo(performingClient, damage);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {

    }
}
