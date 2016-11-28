package model.items.suits;


import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.SpaceProtection;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

public class SpaceSuit extends SuitItem {

	public SpaceSuit() {
		super("Space Suit", 4.0, 500);
	}

	@Override
	public void beingPutOn(Actor actionPerformer) {
		actionPerformer.setCharacter(new SpaceProtection(actionPerformer.getCharacter()));
		
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("spacesuit", "suits.png", 4);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        List<Sprite> list = new ArrayList<>();
        list.add(new Sprite("spacesuithelmet", "head.png", 10));
        return new Sprite("spacesuitworn", "suit.png", 20, list);
    }

    @Override
	public void beingTakenOff(Actor actionPerformer) {
		Logger.log("In being taken off.");
		actionPerformer.removeInstance(new InstanceChecker() {
			
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof SpaceProtection;
			}
		});
	}

	@Override
	public boolean permitsOver() {
		return false;
	}

	@Override
	public SpaceSuit clone() {
		return new SpaceSuit();
	}

}
