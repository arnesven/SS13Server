package model.actions.itemactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.Hazard;
import model.actions.general.Action;
import model.events.ambient.HullBreach;
import model.events.damage.Damager;
import model.items.general.ExplodableItem;
import model.items.general.Explosive;
import model.map.rooms.Room;
import sounds.Sound;
import util.Logger;
import util.MyRandom;

public class ExplosionAction extends Action {

	private Damager exploder;
	private Room location;

	public ExplosionAction(Damager exploder, Room location) {
		super("SpontaneousExplosionEvent", Explosive.SENSED_AS);
		this.exploder = exploder;
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
        ((ExplodableItem)exploder).explode(gameData, location, performingClient);
        location.getItems().remove(exploder);

        new Hazard(gameData) {
            @Override
            public void doHazard(GameData gameData) {
                if (MyRandom.nextDouble() < 0.20) {
                    HullBreach hull = ((HullBreach)gameData.getGameMode().getEvents().get("hull breaches"));
                    hull.startNewEvent(location);
                    Logger.log(Logger.INTERESTING,
                            location.getName() + " got hull brech from " + exploder.getName() + "!");
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

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return Sound.EXPLOSION;
    }
}
