package model.items.suits;

import model.Actor;
import model.items.GameItem;

public abstract class SuitItem extends GameItem {

	private SuitItem under = null;
	
	public SuitItem(String string, double weight) {
		super(string, weight);
	}
	
	public SuitItem getUnder() {
		return under;
	}
	
	public void setUnder(SuitItem it) {
		under = it;
	}
	
	@Override
	public String getName() {
		if (under == null) {
			return super.getName();
		}
		return super.getName() + " (on " + under.getName() + ")";
	}
	
	public double getWeight() {
		if (under == null) {
			return super.getWeight();
		}
		return super.getWeight() + under.getWeight();
	}
	
	public abstract void beingPutOn(Actor actionPerformer);
	public abstract void beingTakenOff(Actor actionPerformer);
	public abstract boolean permitsOver();
	
}
