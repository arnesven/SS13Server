package model.events;

import model.events.damage.ExplosiveDamage;
import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.actions.general.SensoryLevel.AudioLevel;
import model.actions.general.SensoryLevel.OlfactoryLevel;
import model.actions.general.SensoryLevel.VisualLevel;
import model.map.Room;

public class Explosion extends Event {

	public double getProbability() {
		return 0.02;
	}

	@Override
	public void apply(GameData gameData) {
		if (MyRandom.nextDouble() >= getProbability()) {
			return;
		}
		Room room = gameData.getRooms().get(MyRandom.nextInt(gameData.getRooms().size()));
		this.explode(room);

	}

	@Override
	public String howYouAppear(Actor performingClient) {
		return "Explosion!";
	}
	
	public void explode(Room room) {
		for (Target t : room.getTargets()) {
			t.beExposedTo(null, new ExplosiveDamage(1.0));
		}
		room.addToEventsHappened(this);
	}

	@Override
	public SensoryLevel getSense() {
		return new SensoryLevel(VisualLevel.CLEARLY_VISIBLE, 
							    AudioLevel.VERY_LOUD, OlfactoryLevel.UNSMELLABLE);
	}

	@Override
	public String getDistantDescription() {
		return "You hear a loud explosion";
	}

}
