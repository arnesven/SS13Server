package model.characters.general;

import graphics.sprites.Sprite;
import model.Actor;

public abstract class HumanCharacter extends GameCharacter {
	
	public HumanCharacter(String name, int startRoom, double speed) {
		super(name, startRoom, speed);
	}

	public String getPublicName() {
		String res = getBaseName();
		if (getSuit() == null) {
			res = "Naked " + getGender() ;
		}
		if (isDead()) {
			return res + " (dead)";
		}
		return res;
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        Sprite sp = super.getSprite(whosAsking);
        if (isDead()) {
            sp.setRotation(90.0);
        }
        return sp;
    }
}
