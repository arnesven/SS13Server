package model.objects.plants;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.events.PlantUpdater;
import model.map.Room;
import model.objects.SoilPatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 28/11/16.
 */
public abstract class Mushroom extends StagePlant {
    public Mushroom(String name, Room position) {
        super(name, position, 5);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        super.addActions(gameData, cl, at);
        at.add(new PickMushroomAction());
    }

    @Override
    public PlantUpdater getPlantUpdater(GameData gameData, SoilPatch s, Actor planter) {
        return new PlantUpdater(this) {
            public int plantRound = gameData.getRound();

            @Override
            protected void updatePlant(GameData gameData, Plant plant) {
               if (!isMaxStage() && plantRound != gameData.getRound()) {
                   increaseStage();
               }
            }

            @Override
            protected boolean isDone() {
                return isMaxStage();
            }
        };
    }

    private class PickMushroomAction extends Action {

        public PickMushroomAction() {
            super("Pick Mushroom", SensoryLevel.PHYSICAL_ACTIVITY);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "picked a mushroom";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {

        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }
}
