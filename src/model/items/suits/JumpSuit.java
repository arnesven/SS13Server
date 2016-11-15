package model.items.suits;

import graphics.sprites.RegularBlackShoesSprite;
import graphics.sprites.Sprite;
import model.Actor;

import java.util.ArrayList;
import java.util.List;

public class JumpSuit extends SuitItem {

	public JumpSuit() {
		super("Jump Suit", 0.5, 39);
	}

	@Override
	public JumpSuit clone() {
		return new JumpSuit();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("jumpsuit", "uniforms.png", 5, 5);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        List<Sprite> list = new ArrayList<>();
        list.add(new RegularBlackShoesSprite());
        return new Sprite("jumpsuitworn", "uniform.png", 11, 11, 32, 32, list);
    }

    @Override
	public void beingPutOn(Actor actionPerformer) {	}

	@Override
	public void beingTakenOff(Actor actionPerformer) {	}

	@Override
	public boolean permitsOver() {
		return true;
	}

}
