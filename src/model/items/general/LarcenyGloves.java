package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.LarcenistCharacter;
import model.items.suits.SuitItem;

public class LarcenyGloves extends SuitItem {

	public LarcenyGloves() {
		super("Larceny Gloves", 0.1, 299);
	}
	
	

	@Override
	public LarcenyGloves clone() {
		return new LarcenyGloves();
	}

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("glovesworn", "human.png", 0, this);
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
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("larcenygloves", "gloves.png", 0, this);
    }
}
