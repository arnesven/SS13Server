package model.items;

import java.util.NoSuchElementException;

import model.Actor;
import model.characters.GameCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.InstanceRemover;
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
		InstanceRemover rem = new InstanceRemover() {
			
			@Override
			public GameCharacter removeInstance(GameCharacter ch) {
				if (ch instanceof LarcenistCharacter) {
					return ((LarcenistCharacter)ch).getInner();
				} else if (ch instanceof CharacterDecorator) {
					return removeInstance(((CharacterDecorator)ch).getInner());
				}
				throw new NoSuchElementException("Tried to remove instance of LarcenistCharacter but found none!");
			}
		};
		actionPerformer.removeInstance(rem);
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
