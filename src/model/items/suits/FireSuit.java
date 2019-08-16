package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.FireProtection;

import java.util.ArrayList;
import java.util.List;

public class FireSuit extends SuitItem {

	public FireSuit() {
		super("Fire suit", 1.5, 119);
	}

	@Override
	public void beingPutOn(Actor actionPerformer) {
		actionPerformer.setCharacter(new FireProtection(actionPerformer.getCharacter()));
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("firesuit", "suits.png", 0, this);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        List<Sprite> list = new ArrayList<>();
        list.add(new Sprite("firesuithelmet", "head.png", 5, this));
        return new Sprite("firesuitworn", "suit.png", 0, list, this);
    }

    @Override
	public void beingTakenOff(Actor actionPerformer) {
		actionPerformer.removeInstance(new InstanceChecker() {
			
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof FireProtection;
			}
		});
		
	}

	@Override
	public boolean permitsOver() {
		return false;
	}

	@Override
	public FireSuit clone() {
		return new FireSuit();
	}
	

}
