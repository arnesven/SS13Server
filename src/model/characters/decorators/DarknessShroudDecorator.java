package model.characters.decorators;

import model.Actor;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.actions.SensoryLevel.AudioLevel;
import model.actions.SensoryLevel.OlfactoryLevel;
import model.characters.GameCharacter;

public class DarknessShroudDecorator extends CharacterDecorator {

	public DarknessShroudDecorator(GameCharacter inner) {
		super(inner, "Darkness");
	}
	
	@Override
	public String getWatchString(Actor whosAsking) {
		return "It's too dark to see!";
	}
	
	@Override
	public boolean doesPerceive(Action a) {
		SensoryLevel s = a.getSense();
		if (s.sound == AudioLevel.SAME_ROOM || s.sound == AudioLevel.VERY_LOUD) {
			return true;
		}
		if (s.smell != OlfactoryLevel.UNSMELLABLE) {
			return true;
		}
		
		return false;
	}

}
