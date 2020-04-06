package model.items.suits;


import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.SpaceProtection;
import util.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpaceSuit extends FullBodySuit {

	public SpaceSuit() {
		super("Space Suit", 4.0, 500);
	}

	@Override
	public void beingPutOn(Actor actionPerformer) {
		actionPerformer.setCharacter(new SpaceProtection(actionPerformer.getCharacter()));
		
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("spacesuit", "suits.png", 4, this);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        List<Sprite> list = new ArrayList<>();
        list.add(getFullBodySprites().get(Equipment.HEAD_SLOT));
        return new Sprite("spacesuitworn", "suit.png", 20, list, this);
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
	protected Map<Integer, Sprite> getFullBodySprites() {
		Map<Integer, Sprite> map = new HashMap<>();
		map.put(Equipment.HEAD_SLOT, new Sprite("spacesuithelmet", "head.png", 10, this));
		return map;
	}

	@Override
	public SpaceSuit clone() {
		return new SpaceSuit();
	}

}
