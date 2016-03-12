package model.items.suits;

import model.Actor;
import model.characters.decorators.DisguisedAs;

public class OutFit extends SuitItem {

	private String type;

	public OutFit(String name) {
		super(name + "'s Outfit", 0.5);
		this.type = name;
	}


	@Override
	public void beingTakenOff(Actor actionPerformer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean permitsOver() {
		return true;
	}

	@Override
	public void beingPutOn(Actor actionPerformer) {
		actionPerformer.setCharacter(new DisguisedAs(actionPerformer.getCharacter(), type));
	}

}
