package model.events;


import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.SensoryLevel;

public abstract class Event {

	public abstract void apply(GameData gameData);

	public abstract String howYouAppear(Actor performingClient);

	public String addYourselfToRoomInfo(Player whosAsking) {
		return getSprite(whosAsking).getName() + "<img>" + howYouAppear(whosAsking);
	}

	public abstract SensoryLevel getSense();

	public String getDistantDescription() {
		return "";
	}

	public boolean shouldBeRemoved(GameData gameData) {
		return false;
	}

	public void setShouldBeRemoved(boolean b) { }


    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("event", "decals.png", 0, 1, 32, 32);
    }
}
