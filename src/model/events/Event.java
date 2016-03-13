package model.events;

import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.SensoryLevel;

public abstract class Event {

	public abstract double getProbability();

	public abstract void apply(GameData gameData);

	public abstract String howYouAppear(Actor performingClient);

	public String addYourselfToRoomInfo(Player whosAsking) {
		return "e" + howYouAppear(whosAsking);
	}

	public abstract SensoryLevel getSense();

	public String getDistantDescription() {
		return "";
	}

	public boolean shouldBeRemoved(GameData gameData) {
		return false;
	}

	public void setShouldBeRemoved(boolean b) { }
	
}
