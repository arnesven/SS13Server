package model.events;

import model.GameData;
import model.Player;

public interface Event {

	double getProbability();

	void apply(GameData gameData);

	String howYouAppear(Player whosAsking);

}
