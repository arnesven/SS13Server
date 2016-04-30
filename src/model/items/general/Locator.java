package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;

public class Locator extends UplinkItem {

	private Locatable target;


	public Locator() {
		super("Locator", 0.3);
	}
	
	@Override
	public String getFullName(Actor whosAsking) {
		if (target != null) {
			return super.getFullName(whosAsking) + " (" + target.getPosition().getName() + ")";
		}
		return super.getFullName(whosAsking) + " (not found)";
	}

	@Override
	public Locator clone() {
		return new Locator();
	}


	public void setTarget(Locatable locatable) {
		this.target = locatable;
	}
	

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("locator", "device.png", 68);
    }
}
