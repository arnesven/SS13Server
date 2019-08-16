package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.PlantAction;
import model.events.damage.Damager;
import model.items.general.GameItem;
import model.items.seeds.SeedsItem;
import model.items.weapons.Weapon;
import model.map.rooms.Room;
import model.objects.general.BreakableObject;
import model.objects.plants.Plant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 26/11/16.
 */
public class SoilPatch extends BreakableObject {

    private Plant plant;

    public SoilPatch(Room greenhouseRoom) {
        super("Soil Patch", 1000000.0, greenhouseRoom);
    }

    @Override
    public String getBaseName() {
        if (isPlanted()) {
            return plant.getBaseName();
        }
        return super.getBaseName();
    }



    @Override
    public Sprite getSprite(Player whosAsking) {
        Sprite s = new Sprite("soilpatch", "hydroponics.png", 10, 17, this);
        
        if (plant == null) {
            return s;
        }
        List<Sprite> sp = new ArrayList<>();
        sp.add(plant.getSprite(whosAsking));
        return new Sprite("soilpatch" + plant.getSprite(whosAsking).getName(),
                "hydroponics.png", 10, 17, 32, 32, sp, this);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        super.addSpecificActionsFor(gameData, cl, at);

        if (!isPlanted()) {
            for (GameItem g : cl.getItems()) {
                if (g instanceof SeedsItem) {
                    at.add(new PlantAction(this));
                    break;
                }
            }
        }

    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (isPlanted()) {
            plant.addSpecificActionsFor(gameData, cl, at);
        }
    }

    public void plant(SeedsItem selectedSeeds, GameData gameData, Actor planter) {
        this.plant = selectedSeeds.doWhenPlanted(this, gameData, planter);

    }

    public boolean isPlanted() {
        return plant != null;
    }

    public void clearPlant() {
        plant = null;
    }

    @Override
    public boolean beAttackedBy(Actor performingClient, Weapon item) {
        if (!isPlanted()) {
            return false;
        }
        boolean res = plant.beAttackedBy(performingClient, item);
        if (plant.isBroken()) {
            clearPlant();
        }
        return res;
    }

    @Override
    public void beExposedTo(Actor performingClient, Damager damage) {
        if (!isPlanted()) {
            return;
        }
        plant.beExposedTo(performingClient, damage);
        if (plant.isBroken()) {
            clearPlant();
        }
    }
}
