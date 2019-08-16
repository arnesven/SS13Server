package model.events;


import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.SensoryLevel;

import java.io.Serializable;

public abstract class Event extends Experienceable implements SpriteObject, Serializable {

    private boolean shouldBeRemoved = false;
	public abstract void apply(GameData gameData);

	public abstract String howYouAppear(Actor performingClient);

	public final String addYourselfToRoomInfo(Player whosAsking) {
		return getSprite(whosAsking).getName() + "<img>" + howYouAppear(whosAsking);
	}

	public abstract SensoryLevel getSense();

	public String getDistantDescription() {
		return "";
	}

	public boolean shouldBeRemoved(GameData gameData) {
		return shouldBeRemoved;
	}

	public void setShouldBeRemoved(boolean b) { }


    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("event", "decals2.png", 5, 0, 32, 32, this);
    }

    public double getProbability() {
        return 0.0;
    }

    public void setProbability(double v) {
    }

    public void experienceFar(Player p) {
        String text = this.getDistantDescription();
        p.addUniquelyTolastTurnInfo(text);
    }

    public void experienceNear(Player p) {

    }

    public String getPublicName(Actor whosAsking) {
	    return howYouAppear(whosAsking);
    }


    public interface EventRunner {
        void doEvent(GameData gd);
    }

    public static void runOnceAtEndOfMovement(GameData gd, EventRunner ev) {
        gd.addMovementEvent(new Event() {
            @Override
            public void apply(GameData gameData) {
                ev.doEvent(gameData);
            }

            @Override
            public String howYouAppear(Actor performingClient) {
                return "";
            }

            @Override
            public SensoryLevel getSense() {
                return SensoryLevel.NO_SENSE;
            }

            @Override
            public boolean shouldBeRemoved(GameData gameData) {
                return true;
            }
        });
    }
}
