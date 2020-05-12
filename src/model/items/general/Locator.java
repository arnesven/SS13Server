package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;

public class Locator extends UplinkItem {

	private Locatable target;


	public Locator() {
		super("Locator", 0.3, 500);
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

	public Locatable getTarget() {
		return target;
	}

	@Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("locator", "device.png", 68, this);
    }

	@Override
	public String getDescription(GameData gameData, Player performingClient) {
		return "A device for locating other person or devices. Handy for thieves and assassins alike.";
	}
}
