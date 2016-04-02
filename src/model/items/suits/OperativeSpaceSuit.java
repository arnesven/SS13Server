package model.items.suits;

import model.Actor;
import model.characters.decorators.OperativeSpaceProtection;

public class OperativeSpaceSuit extends SpaceSuit {

	public OperativeSpaceSuit() {
		this.setWeight(5.0);
	}
	
	@Override
	public void beingPutOn(Actor actionPerformer) {
		actionPerformer.setCharacter(new OperativeSpaceProtection(actionPerformer.getCharacter()));
	}
}
