package model.events;

import java.util.List;

import model.GameData;
import model.Player;

public abstract class Event {

	public abstract double getProbability();

	public abstract void apply(GameData gameData);

	public abstract String howYouAppear(Player whosAsking);

	public String addYourselfToRoomInfo(Player whosAsking) {
		return "e" + howYouAppear(whosAsking);
	}

}
