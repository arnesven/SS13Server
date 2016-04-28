package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;

public class NuclearDisc extends GameItem {

	public NuclearDisc() {
		super("Nuclear Disc", 0.1);
	}

	@Override
	public NuclearDisc clone() {
		return new NuclearDisc();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("nucleardisc","cloning.png", 9);
    }


}
