package model.items.suits;

import model.Actor;
import model.characters.general.GameCharacter;
import model.characters.decorators.FancyClothesDecorator;
import model.characters.decorators.InstanceChecker;

public class FancyClothes extends SuitItem {

	
	public FancyClothes() {
		super("Fancy Clothes", 0.5);
	}

	@Override
	public void beingPutOn(Actor actionPerformer) {
		actionPerformer.addTolastTurnInfo("You got all dressed up!");
		actionPerformer.setCharacter(new FancyClothesDecorator(actionPerformer.getCharacter()));
	}
	

	@Override
	public void beingTakenOff(Actor actionPerformer) {
		actionPerformer.removeInstance(new InstanceChecker() {
			
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof FancyClothesDecorator;
			}
		});
		
	}
	
	@Override
	public FancyClothes clone() {
		return new FancyClothes();
	}

	@Override
	public boolean permitsOver() {
		return true;
	}

}
