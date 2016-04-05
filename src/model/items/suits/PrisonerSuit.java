package model.items.suits;

import model.Actor;
import model.characters.GameCharacter;
import model.characters.decorators.InstanceRemoverAdapter;
import model.characters.decorators.InstanceChecker;

public class PrisonerSuit extends SuitItem {

	private int number;

	public PrisonerSuit(int i) {
		super("Prison Clothes", 0.5);
		this.number = i;
	}

	@Override
	public PrisonerSuit clone() {
		return new PrisonerSuit(number);
	}

	@Override
	public void beingPutOn(Actor actionPerformer) { 
		System.out.println(actionPerformer.getBaseName() + " put on the prisoner clothes.");
		actionPerformer.setCharacter(new PrisonerDecorator(actionPerformer.getCharacter(), this.number));
	}

	@Override
	public void beingTakenOff(Actor actionPerformer) { 
		actionPerformer.removeInstance(new InstanceRemoverAdapter(new InstanceChecker() {
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof PrisonerDecorator;
			}
		}));
	}

	@Override
	public boolean permitsOver() {
		return true;
	}

}
