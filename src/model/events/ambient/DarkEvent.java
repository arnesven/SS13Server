package model.events.ambient;

import graphics.sprites.Sprite;
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
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("darkeventspr", "buttons1.png", 1, 3);
    }

    @Override
	public SensoryLevel getSense() {
		return new SensoryLevel(SensoryLevel.VisualLevel.CLEARLY_VISIBLE,
								SensoryLevel.AudioLevel.INAUDIBLE,
								SensoryLevel.OlfactoryLevel.UNSMELLABLE);
	}

}
