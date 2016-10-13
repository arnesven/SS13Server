package model.events;


import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.SensoryLevel;

import java.io.Serializable;

public abstract class Event implements Serializable {

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
        return new Sprite("event", "decals2.png", 5, 0, 32, 32);
    }

    public double getProbability() {
        return 0.0;
    }

    public void setProbability(double v) {
    }


}
