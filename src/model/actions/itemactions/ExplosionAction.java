package model.actions.itemactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.items.Explosive;
import model.items.GameItem;
import model.items.Grenade;
import model.map.Room;

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
	}

	@Override
	public void setArguments(List<String> args, Actor p) { }

	@Override
	protected String getVerb(Actor whosAsking) {
		return "exploded";
	}

}
