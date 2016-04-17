package model.events;


import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.SensoryLevel;

public abstract class Event {

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
