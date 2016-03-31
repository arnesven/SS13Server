package model.items.suits;

import java.util.NoSuchElementException;

import model.Actor;
import model.characters.GameCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.FancyClothesDecorator;
import model.characters.decorators.InstanceRemover;

public class FancyClothes extends SuitItem {

	
	public FancyClothes() {
		super("Fancy Clothes", 0.5);
	}

	@Override
	public void beingPutOn(Actor actionPerformer) {
		actionPerformer.addTolastTurnInfo("You got all dressed up!");
		actionPerformer.setCharacter(new FancyClothesDecorator(actionPerformer.getCharacter()));
	}
	
//	@Override
//	public String getPublicName(Actor whosAsking) {
//		return "Fancy Clothes";
//	}
	
//	@Override
//	public String getFullName(Actor whosAsking) {
//		return super.getFullName(whosAsking)"Fancy Clothes";
//	}

	@Override
	public void beingTakenOff(Actor actionPerformer) {
		InstanceRemover fancyRemover = new InstanceRemover() {
			
			@Override
			public GameCharacter removeInstance(GameCharacter ch) {
				if (ch instanceof FancyClothesDecorator) {
					return ((CharacterDecorator)ch).getInner();
				}
				if (ch instanceof CharacterDecorator) {
					return removeInstance(((CharacterDecorator)ch).getInner());
				}
				
				throw new NoSuchElementException("Did not find that instance!");
			}
		};
		
		actionPerformer.removeInstance(fancyRemover);
		
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
