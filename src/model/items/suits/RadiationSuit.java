package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.RadiationProtection;

import java.util.ArrayList;
import java.util.List;

public class RadiationSuit extends SuitItem {

	public RadiationSuit() {
		super("Radiation Suit", 2.0, 99);
	}

	@Override
	public RadiationSuit clone() {
		return new RadiationSuit();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("radiationsuit", "suits.png", 5);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        List<Sprite> list = new ArrayList<>();
        list.add(new Sprite("radiationsuithelmet", "head.png", 5));
        return new Sprite("radiationsuitworn", "suit2.png", 11, 2, 32, 32, list);
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

}
