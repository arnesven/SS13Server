package model.actions;

import java.util.Comparator;

import model.Actor;
import model.characters.general.CharacterSpeedComparator;

public class SpeedComparator implements Comparator<Actor> {

	@Override
	public int compare(Actor o1, Actor o2) {
		CharacterSpeedComparator comp = new CharacterSpeedComparator();
		return comp.compare(o1.getCharacter(), o2.getCharacter());
	}

}
