package model.characters.crew;

import model.characters.GameCharacter;
import model.characters.HumanCharacter;
import model.items.suits.OutFit;

public abstract class CrewCharacter extends HumanCharacter {

	public CrewCharacter(String name, int startRoom, double speed) {
		super(name, startRoom, speed);
		putOnSuit(new OutFit(name));
	}
	

}
