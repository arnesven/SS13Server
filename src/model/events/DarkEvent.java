package model.events;

import model.Actor;
import model.GameData;
import model.actions.SensoryLevel;

public class DarkEvent extends Event {

	@Override
	public void apply(GameData gameData) { }

	@Override
	public String howYouAppear(Actor performingClient) {
		return "Dark";
	}

	@Override
	public SensoryLevel getSense() {
		return new SensoryLevel(SensoryLevel.VisualLevel.CLEARLY_VISIBLE,
								SensoryLevel.AudioLevel.INAUDIBLE,
								SensoryLevel.OlfactoryLevel.UNSMELLABLE);
	}

}
