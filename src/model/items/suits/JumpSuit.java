package model.items.suits;

import graphics.sprites.RegularBlackShoesSprite;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JumpSuit extends TorsoAndShoesSuit {

	public JumpSuit() {
		super("Jump Suit", 0.5, 39);
	}

	@Override
	public JumpSuit clone() {
		return new JumpSuit();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("jumpsuit", "uniforms.png", 7, 1, this);
    }

	@Override
	public String getDescription(GameData gameData, Player performingClient) {
		return "A suit which is designed to be worn underneath another suit.";
	}

	@Override
    protected Sprite getWornSprite(Actor whosAsking) {
        List<Sprite> list = new ArrayList<>();
        list.add(new RegularBlackShoesSprite());
        return new Sprite("jumpsuitworn", "uniform.png", 15, 0, 32, 32, list, this);
    }

    @Override
	public void beingPutOn(Actor actionPerformer) {	}

	@Override
	public void beingTakenOff(Actor actionPerformer) {	}

	@Override
	public boolean permitsOver() {
		return true;
	}

	@Override
	protected Map<Integer, Sprite> getOtherSprites() {
		Map<Integer, Sprite> map = new HashMap<>();
		map.put(Equipment.FEET_SLOT, RegularBlackShoesSprite.EQ_SPRITE);
		return map;
	}

}
