package model.events;

import util.MyRandom;
import model.Actor;
import model.GameData;
import model.actions.SensoryLevel;
import model.actions.SensoryLevel.AudioLevel;
import model.actions.SensoryLevel.OlfactoryLevel;
import model.actions.SensoryLevel.VisualLevel;
import model.actions.Target;
import model.map.Room;

public class Explosion extends Event {

	@Override
	public double getProbability() {
		return 0.05;
	}

	@Override
	public void apply(GameData gameData) {
		Room room = gameData.getRooms().get(MyRandom.nextInt(gameData.getRooms().size()));
		
		for (Target t : room.getTargets()) {
			t.beExposedTo(null, new Damager() {
				
				@Override
				public boolean isDamageSuccessful(boolean reduced) {
					return true;
				}
				
				@Override
				public String getText() {
					return "You were struck down by a violent explosion!";
				}
				
				@Override
				public String getName() {
					return "explosion";
				}
				
				@Override
				public double getDamage() {
					return 1.0;
				}
			});
		}
		room.addToEventsHappened(this);
	}

	@Override
	public String howYouAppear(Actor performingClient) {
		return "Explosion!";
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
