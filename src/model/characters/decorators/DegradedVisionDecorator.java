package model.characters.decorators;

import model.Actor;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.general.SensoryLevel.AudioLevel;
import model.actions.general.SensoryLevel.OlfactoryLevel;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.LightItem;

public class DegradedVisionDecorator extends CharacterDecorator {

	private final String description;

	public DegradedVisionDecorator(GameCharacter inner, String description) {
		super(inner, "Darkness");
		this.description = description;
	}


	@Override
	public String getWatchString(Actor whosAsking) {
        if (GameItem.hasAnItem(whosAsking, LightItem.class)) {
            super.getWatchString(whosAsking);
        }
		return description;
	}
	
	@Override
	public boolean doesPerceive(Action a) {
		SensoryLevel s = a.getSense();
		if (s.sound == AudioLevel.SAME_ROOM || s.sound == AudioLevel.VERY_LOUD) {
			return true;
		}
        return s.smell != OlfactoryLevel.UNSMELLABLE;

    }

    @Override
    public boolean isVisibileFromAdjacentRoom() {
        return false;
    }
}
