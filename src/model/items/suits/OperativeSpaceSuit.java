package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.decorators.OperativeSpaceProtection;

import java.util.ArrayList;
import java.util.List;

public class OperativeSpaceSuit extends JetPackSpaceSuit {

	public OperativeSpaceSuit() {
		this.setWeight(5.0);
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("operativespacesuit", "suits.png", 8, this);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        List<Sprite> list = new ArrayList<>();
        list.add(new Sprite("operativespacesuithelmet", "head.png", 8, 1, this));
        return new Sprite("operativespacesuitworn", "suit.png", 45, list, this);
    }

    @Override
	public void beingPutOn(Actor actionPerformer) {
	    super.beingPutOn(actionPerformer);
		actionPerformer.setCharacter(new OperativeSpaceProtection(actionPerformer.getCharacter()));
	}
}
