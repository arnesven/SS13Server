package model.actions.general;

import model.Actor;
import model.GameData;
import model.map.rooms.ExoticPlanet;

import java.util.List;

/**
 * Created by erini02 on 15/09/17.
 */
public class ExplorePlanetAction extends Action {
    private final ExoticPlanet planet;

    public ExplorePlanetAction(ExoticPlanet exoticPlanet) {
        super("Explore Planet", SensoryLevel.PHYSICAL_ACTIVITY);
        this.planet = exoticPlanet;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "explored the planet";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        performingClient.addTolastTurnInfo(planet.getDescription());
        planet.setExplored(true, gameData);
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}
