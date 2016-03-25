package model.items;

import model.Actor;

public class Locator extends GameItem {

	private Locatable target;


	public Locator() {
		super("Locator", 0.3);
	}
	
	@Override
	public String getFullName(Actor whosAsking) {
		return super.getFullName(whosAsking) + " (" + target.getPosition().getName() + ")";
	}

	@Override
	public Locator clone() {
		return new Locator();
	}


	public void setTarget(Locatable locatable) {
		this.target = locatable;
	}

}
