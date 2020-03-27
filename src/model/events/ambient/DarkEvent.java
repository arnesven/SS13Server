package model.events.ambient;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.events.Event;
import model.map.rooms.Room;

public class DarkEvent extends Event {

	private final static Sprite darkSprite = new Sprite("darksprite", "alert.png", 3, 2, null);

	@Override
	public void apply(GameData gameData) { }

	@Override
	public String howYouAppear(Actor performingClient) {
		return "Dark";
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        // Should not be used anymore since showSpriteInRoom() returns false;
		return new Sprite("darkeventspr", "buttons1.png", 1, 3, this);
    }

    @Override
	public SensoryLevel getSense() {
		return new SensoryLevel(SensoryLevel.VisualLevel.CLEARLY_VISIBLE,
								SensoryLevel.AudioLevel.INAUDIBLE,
								SensoryLevel.OlfactoryLevel.UNSMELLABLE);
	}

	@Override
	public boolean showSpriteInRoom() {
		return false;
	}


	@Override
	public void gotAddedToRoom(Room room) {
		room.addEffect(darkSprite);
	}

	@Override
	public void gotRemovedFromRoom(Room room) {
		room.getEffects().remove(darkSprite);
	}
}
