package model.items.general;

import model.Actor;
import model.characters.GameCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.LarcenistCharacter;
import model.items.suits.SuitItem;

public class LarcenyGloves extends SuitItem {

	public LarcenyGloves() {
		super("Larceny Gloves", 0.1);
	}
	
	

	@Override
	public LarcenyGloves clone() {
		return new LarcenyGloves();
	}



	@Override
	public void beingPutOn(Actor actionPerformer) {
		actionPerformer.setCharacter(new LarcenistCharacter(actionPerformer.getCharacter()));
	}



	@Override
	public void beingTakenOff(Actor actionPerformer) {
		actionPerformer.removeInstance(new InstanceChecker() {
			
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof LarcenistCharacter;
			}
		});
	}



	@Override
	public boolean permitsOver() {
		return true;
	}
	
	@Override
	protected char getIcon() {
		return '2';
	}

}
