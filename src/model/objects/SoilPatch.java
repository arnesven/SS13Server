package model.objects;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.PlantAction;
import model.items.general.GameItem;
import model.items.seeds.SeedsItem;
import model.map.GreenhouseRoom;
import model.map.Room;
import model.objects.general.GameObject;
import model.objects.plants.Plant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 26/11/16.
 */
public class SoilPatch extends GameObject {

    private Plant plant;

    public SoilPatch(Room greenhouseRoom) {
        super("Soil Patch", greenhouseRoom);
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        if (isPlanted()) {
            return plant.getPublicName(whosAsking);
        }
        return super.getPublicName(whosAsking);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        Sprite s = new Sprite("soilpatch", "hydroponics.png", 10, 17);
        
        if (plant == null) {
            return s;
        }
        List<Sprite> sp = new ArrayList<>();
        sp.add(plant.getSprite(whosAsking));
        return new Sprite("soilpatch" + plant.getSprite(whosAsking).getName(),
                "hydroponics.png", 10, 17, 32, 32, sp);
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

    public void plant(SeedsItem selectedSeeds, GameData gameData, Actor planter) {
        this.plant = selectedSeeds.doWhenPlanted(this, gameData, planter);

    }

    public boolean isPlanted() {
        return plant != null;
    }

    public void clearPlant() {
        plant = null;
    }
}
