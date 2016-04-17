package model.events.ambient;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.events.Event;

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
