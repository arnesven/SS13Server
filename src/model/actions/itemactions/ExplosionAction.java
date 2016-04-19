package model.actions.itemactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.Hazard;
import model.actions.general.Action;
import model.events.ambient.HullBreach;
import model.items.general.Explosive;
import model.items.general.Grenade;
import model.map.Room;
import model.objects.general.BreakableObject;
import model.objects.general.GameObject;
import util.MyRandom;

public class ExplosionAction extends Action {

	private Grenade grenade;
	private Room location;

	public ExplosionAction(Grenade grenade, Room location) {
		super("Explosion", Explosive.SENSED_AS);
		this.grenade = grenade;
		this.location = location;
	}

	@Override
	public String getDescription(Actor whosAsking) {
		return "There was a violent explosion.";
	}
	
	@Override
	public String getDistantDescription(Actor whosAsking) {
		return "You hear a loud explosion.";
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		for (Actor a : location.getActors()) {
			a.getAsTarget().beExposedTo(performingClient, grenade);
			location.getItems().remove(grenade);
		}
        for (GameObject o : location.getObjects()) {
            ((BreakableObject)o).beExposedTo(performingClient, grenade);
        }

        new Hazard(gameData) {
            @Override
            public void doHazard(GameData gameData) {
                if (MyRandom.nextDouble() < 0.20) {
                    HullBreach hull = ((HullBreach)gameData.getGameMode().getEvents().get("hull breaches"));
                    hull.startNewEvent(location);
                    System.out.println(location.getName() + " got hull brech from grenade!");
                }
            }
        };
    }


    @Override
	public void setArguments(List<String> args, Actor p) { }

	@Override
	protected String getVerb(Actor whosAsking) {
		return "exploded";
	}

}
