package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.FireProtection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireSuit extends FullBodySuit {

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
        list.add(getFullBodySprites().get(Equipment.HEAD_SLOT));
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
	protected Map<Integer, Sprite> getFullBodySprites() {
		Map<Integer, Sprite> map = new HashMap<>();
		map.put(Equipment.HEAD_SLOT, new Sprite("firesuithelmet", "head.png", 5, this));
		return map;
	}

	@Override
	public FireSuit clone() {
		return new FireSuit();
	}
	

}
