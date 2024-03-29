package model.events;


import graphics.ClientInfo;
import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.map.rooms.Room;
import sounds.Sound;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Event extends Experienceable implements SpriteObject, Serializable {

    private boolean shouldBeRemoved = false;
	public abstract void apply(GameData gameData);

	public abstract String howYouAppear(Actor performingClient);

	public final String addYourselfToRoomInfo(Actor whosAsking) {
		return getSprite(whosAsking).getName() + "<img>" + howYouAppear(whosAsking) + "<img>{}";
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

    public Sprite getRoomSprite(Actor whosAsking) {
	    return getSprite(whosAsking);
    }

    @Override
    public String getEffectIdentifier(Actor whosAsking) {
        return getSprite(whosAsking).getName();
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

    public void gotAddedToRoom(Room room) { }

    public void gotRemovedFromRoom(Room room) { }

    public boolean showSpriteInRoom() {
        return true;
    }

    public boolean showSpriteInTopPanel() {
	    return !showSpriteInRoom();
    }

    public boolean hasAmbientSound() {
        return false;
    }

    public Sound getAmbientSound() {
	    return null;
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


    public List<Action> getOverlaySpriteActionList(GameData gameData, Room r, Player forWhom) {
        return new ArrayList<>();
    }

    @Override
    public boolean hasAbsolutePosition() {
        return false;
    }

    @Override
    public void setAbsolutePosition(double x, double y, double z) {
    }

    @Override
    public double getAbsoluteX() {
        return 0;
    }

    @Override
    public double getAbsoluteY() {
        return 0;
    }

    @Override
    public double getAbsoluteZ() {
        return 0;
    }
}
