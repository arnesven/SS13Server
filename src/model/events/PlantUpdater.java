package model.events;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.objects.plants.Plant;
import model.objects.plants.TomatoPlant;

/**
 * Created by erini02 on 27/11/16.
 */
public abstract class PlantUpdater extends Event {
    private final Plant plant;

    public PlantUpdater(Plant pl) {
        this.plant = pl;
    }


    @Override
    public void apply(GameData gameData) {
        updatePlant(gameData, plant);
    }

    protected abstract void updatePlant(GameData gameData, Plant plant);

    @Override
    public String howYouAppear(Actor performingClient) {
        return "";
    }

    @Override
    public SensoryLevel getSense() {
        return SensoryLevel.NO_SENSE;
    }
}
