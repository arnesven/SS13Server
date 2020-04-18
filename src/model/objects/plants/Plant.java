package model.objects.plants;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.events.PlantUpdater;
import model.events.damage.Damager;
import model.events.damage.RadiationDamage;
import model.items.seeds.SeedsItem;
import model.map.rooms.Room;
import model.objects.general.SoilPatch;
import model.objects.general.BreakableObject;

import java.util.ArrayList;

/**
 * Created by erini02 on 26/11/16.
 */
public abstract class Plant extends BreakableObject {
    private SoilPatch soilPatch;

    public Plant(String name, Room position, SoilPatch sp) {
        super(name, 0.5, position);
        this.soilPatch = sp;
    }

    public abstract PlantUpdater getPlantUpdater(GameData gameData,
                                                 SoilPatch s, Actor planter);

    public abstract SeedsItem getSeeds();

    public SoilPatch getSoilPatch() {
        return soilPatch;
    }

    @Override
    public void beExposedTo(Actor performingClient, Damager damage, GameData gameData) {
        if (damage instanceof RadiationDamage) {
            return;
        }
        super.beExposedTo(performingClient, damage, gameData);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {

    }
}
