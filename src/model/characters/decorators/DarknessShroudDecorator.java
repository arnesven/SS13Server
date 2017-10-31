package model.characters.decorators;

import model.Actor;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.general.SensoryLevel.AudioLevel;
import model.actions.general.SensoryLevel.OlfactoryLevel;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.LightItem;

public class DarknessShroudDecorator extends CharacterDecorator {

	public DarknessShroudDecorator(GameCharacter inner) {
		super(inner, "Darkness");
	}
	
	@Override
	public String getWatchString(Actor whosAsking) {
        if (GameItem.hasAnItem(whosAsking, LightItem.class)) {
            super.getWatchString(whosAsking);
        }
		return "It's too dark to see!";
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
