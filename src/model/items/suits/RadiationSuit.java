package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.RadiationProtection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RadiationSuit extends FullBodySuit {

	public RadiationSuit() {
		super("Radiation Suit", 2.0, 99);
	}

	@Override
	public RadiationSuit clone() {
		return new RadiationSuit();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("radiationsuit", "suits.png", 5, this);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        List<Sprite> list = new ArrayList<>();
        list.add(getFullBodySprites().get(Equipment.HEAD_SLOT));
        return new Sprite("radiationsuitworn", "suit2.png", 11, 2, 32, 32, list, this);
    }

    @Override
	public void beingPutOn(Actor actionPerformer) {
		actionPerformer.setCharacter(new RadiationProtection(actionPerformer.getCharacter()));
	}

	@Override
	public void beingTakenOff(Actor actionPerformer) {
        if (actionPerformer.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof RadiationProtection)) {
            actionPerformer.removeInstance(new InstanceChecker() {

                @Override
                public boolean checkInstanceOf(GameCharacter ch) {
                    return ch instanceof RadiationProtection;
                }
            });
        }
		
	}

	@Override
	public boolean permitsOver() {
		return false;
	}

    @Override
    protected Map<Integer, Sprite> getFullBodySprites() {
	    Map<Integer, Sprite> map = new HashMap<>();
	    map.put(Equipment.HEAD_SLOT, new Sprite("radiationsuithelmet", "head.png", 5, this));

        return map;
    }

}
