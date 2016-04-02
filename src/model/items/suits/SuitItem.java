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
	
	@Override
	protected char getIcon() {
		return 'U';
	}
	
	public void setUnder(SuitItem it) {
		under = it;
	}
	
	@Override
	public abstract SuitItem clone();
	
	@Override
	public String getFullName(Actor whosAsking) {
		if (under == null) {
			return super.getFullName(whosAsking);
		}
		return super.getFullName(whosAsking) + " (on " + under.getFullName(whosAsking) + ")";
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
